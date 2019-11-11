package com.rysl.unigradejav;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rysl.unigradejav.src.Database.DatabaseHelper;
import com.rysl.unigradejav.src.Database.sqlAccess_final;
import com.rysl.unigradejav.src.learningTree.Learner;
import com.rysl.unigradejav.src.userInterface.RecyclerViewClickListener;
import com.rysl.unigradejav.src.userInterface.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {
    sqlAccess_final mySQLLite;
    RecyclerViewInterface reView;
    boolean menuVisible = false;
    CardView addMenu;
    Button menuButton, submitButton, addDescription, submitDescription;
    TextView menuTitle, percentTitle, typeTitle, tableName, title, percent, textType, menuPercent,
            currentResult, finalPercent, description;
    EditText menuName, editDescription;
    final String[] tables = {"subject", "module", "assignment", "end", "card"};
    int tableIndex = 0;
    Learner currentLearner = null;
    ArrayList<Learner> currentLearners = new ArrayList<>();
    Stack<Learner> pastLearners = new Stack<>();
    Vibrator vibrator;
    Spinner spinnerType;
    SeekBar percentBar, finalPercentBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUI();
    }

    public void createUI(){
        RecyclerView view = findViewById(R.id.col1);

        mySQLLite = new sqlAccess_final(new DatabaseHelper(this));
        currentLearners = getLearner();

        percentBar = findViewById(R.id.percentBar);
        percentBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                menuPercent.setText(percentBar.getProgress()+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        reView = new RecyclerViewInterface(this, view, currentLearners, this);

        tableName = findViewById(R.id.tableName);
        setTable();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        makeMenuWidgets();
    }

    public void setTable(){
        tableName.setText(tables[tableIndex]);
    }

    public void makeFinalCardWidgets(){
        title = findViewById(R.id.title);
        percent = findViewById(R.id.percent);
        textType = findViewById(R.id.type);
        title.setText(currentLearner.getName());
        percent.setText(currentLearner.getPercentage()+"%");
        String typeContent = "Coursework";
        if(currentLearner.getType()){
            typeContent = "Test";
        }
        textType.setText(typeContent);
        submitButton = findViewById(R.id.submitButton);
        finalPercent = findViewById(R.id.resultBar);
        finalPercentBar = findViewById(R.id.result);
        addDescription = findViewById(R.id.enterDescription);
        description = findViewById(R.id.description);
        description.setText(getDescription());
        currentResult = findViewById(R.id.currentResult);
        currentResult.setText(getResult());
        finalPercentBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                finalPercent.setText(finalPercentBar.getProgress()+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySQLLite.setResult(currentLearner, finalPercentBar.getProgress());
                currentResult.setText(getResult());
            }
        });
        addDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableIndex++;
                setContentView(R.layout.description_former);
                makeDescriptionFormer();
            }
        });
    }

    public void makeDescriptionFormer(){
        submitDescription = findViewById(R.id.submitDescription);
        editDescription = findViewById(R.id.editDescription);
        submitDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySQLLite.setDescription(currentLearner, editDescription.getText().toString());
                tableIndex--;
                setContentView(R.layout.final_card_layout);
                makeFinalCardWidgets();
            }
        });
    }

    public String getResult(){
        int result = mySQLLite.getResult(currentLearner);
        String resultText = "Current result: " + result + "%";
        if(result == -1){
            resultText = "No result entered";
        }
        return resultText;
    }

    public String getDescription(){
        String databaseDescription = mySQLLite.getDescription(currentLearner);
        if(databaseDescription == null){
            addDescription.setText("add description");
            return "No description set";
        }
        addDescription.setText("edit description");
        return databaseDescription;
    }

    @SuppressLint("RestrictedApi")
    public void createLearnerMenu(final String table){
        menuName.setText("");
        if(tables[tableIndex] != "end") {
            if (menuVisible) {
                addMenu.setVisibility(View.GONE);
                setPercentVisble(false);
                setTypeVisible(false);
            } else {
                menuTitle.setText(table);
                boolean boolType = false;
                if (tables[tableIndex] == "assignment") {
                    percentBar.setMax(getMaxBarValue());
                    boolType = true;
                }
                setPercentVisble(boolType);
                setTypeVisible(boolType);
                addMenu.setVisibility(View.VISIBLE);
                enterButton(boolType, true, null, -1);
            }
            menuVisible = !menuVisible;
        }
    }

    public void editLearnerMenu(int position){
        Learner learnerToEdit = currentLearners.get(position-1);
        menuTitle.setText(learnerToEdit.getName());
        menuName.setText("");
        boolean boolType = false;
        if(tables[tableIndex] == "assignment"){
            percentBar.setMax(getMaxBarValue()+learnerToEdit.getPercentage());
            boolType = true;
        }
        setPercentVisble(boolType);
        setTypeVisible(boolType);
        addMenu.setVisibility(View.VISIBLE);
        enterButton(boolType, false, learnerToEdit, position);
        menuVisible = !menuVisible;
    }

    public void enterButton(final boolean boolType, final boolean isAdd, final Learner learner, final int position){
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMenu(boolType, isAdd, learner, position);
            }
        });
    }

    public int getMaxBarValue(){
        int maxValue = 0;
        for(int x = 0; x<currentLearners.size(); x++){
            maxValue += currentLearners.get(x).getPercentage();
        }
        return 100-maxValue;
    }

    public void enterMenu(boolean type, boolean isAdd, Learner learner, int position){
        String name = menuName.getText().toString();
        int percent = -1;
        Boolean booltype = null;
        if (type) {
            percent = percentBar.getProgress();
            booltype = getType();
        }
        hideKeyboard(this);
        if(isAdd) {
            mySQLLite.setLearner(currentLearner, name, percent, booltype);
            updateButtonsFromLearners();
        } else{
            currentLearners.set(position-1, learner);
            mySQLLite.editLearner(learner, name, percent, booltype);
            updateDataset(position);
        }
        addMenu.setVisibility(View.GONE);
        menuVisible = !menuVisible;
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public boolean getType(){
        String typeName = spinnerType.getSelectedItem().toString();
        if(typeName == "Test"){
            return true;
        }
        return false;
    }

    public void setPercentVisble(boolean visible){
        if(visible){
            percentTitle.setVisibility(View.VISIBLE);
            menuPercent.setVisibility(View.VISIBLE);
            percentBar.setVisibility(View.VISIBLE);
        }else{
            percentTitle.setVisibility(View.GONE);
            menuPercent.setVisibility(View.GONE);
            percentBar.setVisibility(View.GONE);
        }
    }

    public void setTypeVisible(boolean visible){
        if(visible){
            typeTitle.setVisibility(View.VISIBLE);
            spinnerType.setVisibility(View.VISIBLE);
        }else{
            spinnerType.setVisibility(View.GONE);
            typeTitle.setVisibility(View.GONE);
        }
    }

    public void makeMenuWidgets(){
        addMenu = findViewById(R.id.addMenu);
        menuButton = findViewById(R.id.testButton);
        menuName = findViewById(R.id.testInput);
        menuPercent = findViewById(R.id.testInput2);
        menuTitle = findViewById(R.id.menuTitle);
        percentTitle = findViewById(R.id.percentTitle);
        typeTitle = findViewById(R.id.typeTitle);
        spinnerType = findViewById(R.id.spinner);
        String[] types = new String[]{"Test", "Coursework"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }

    public void updateButtonsFromLearners(){
        currentLearners = getLearner();
        reView.addLearner(currentLearners);
    }

    public void changeDataset(){
        if(tables[tableIndex] == "end"){
            setContentView(R.layout.final_card_layout);
            makeFinalCardWidgets();
        } else {
            currentLearners = getLearner();
            reView.refreshView(currentLearners);
        }
    }

    public void updateDataset(int position){
        Learner updatedLearner = getLearner().get(position-1);
        currentLearners.set(position-1, updatedLearner);
        reView.updateLearner(updatedLearner, position);
    }

    public ArrayList<Learner> getLearner(){
        ArrayList<Learner> learners = mySQLLite.getLearner(currentLearner, tables[tableIndex]);
        for(Learner learner : learners){
            learner.setWorkingPercent(mySQLLite.getWorkingPercent(learner, 0));
        }
        return learners;
    }

    public void updateCard(){
        menuTitle.setText(tables[tableIndex]);
        tableName.setText(tables[tableIndex]);
    }

    public void deleteFromDatabase(Learner learner){
        mySQLLite.deleteLearner(learner.getKey(), learner.getTable());
    }


    //CREDIT: https://stackoverflow.com/users/680583/rmirabelle
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //---------------------------------------------------------

    @Override
    public void recyclerViewListClicked(int position){
        if(position == 0){
            createLearnerMenu(tables[tableIndex]);
        }else {
            if (!menuVisible) {
                pastLearners.push(currentLearner);
                currentLearner = currentLearners.get(position-1);
                tableIndex++;
                updateCard();
                changeDataset();
                vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
    }

    @Override
    public void recyclerViewListSwiped(int position){
        if(!menuVisible) {
            reView.deleteLearner(position);
            deleteFromDatabase(currentLearners.get(position - 1));
            currentLearners.remove(position - 1);
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    @Override
    public void recyclerViewListLongClicked(int position){
        if(!menuVisible && position != 0){
            editLearnerMenu(position);
            vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    @Override
    public String getTable(){
        return tables[tableIndex];
    }

    @Override
    public void onBackPressed(){
        if (menuVisible) {
            createLearnerMenu(tables[tableIndex]);
        } else {
            tableIndex--;
            if (tableIndex < 3) {
                currentLearner = pastLearners.pop();
            }
                updateCard();
                changeDataset();
        }
        if (tables[tableIndex] == "assignment") {
            setContentView(R.layout.activity_main);
            createUI();
        }
    }
}
