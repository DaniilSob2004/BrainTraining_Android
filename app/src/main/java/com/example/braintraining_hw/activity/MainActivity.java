package com.example.braintraining_hw.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.braintraining_hw.R;
import com.example.braintraining_hw.model.Question;
import com.example.braintraining_hw.utils.AppConstant;
import com.example.braintraining_hw.utils.FileWork;
import com.example.braintraining_hw.utils.TaskFactory;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView currNumQuestionTextView;
    private TextView questionTextView;
    private TextView correctNumAnswerTextView;
    private TextView countQuestionTextView;
    private ImageButton correctBtn;
    private ImageButton incorrectBtn;

    private List<Question> questions;
    private int currNumQuestion;
    private Question currQuestion;
    private int correctNumAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // инициализация SharedPreferences
        FileWork.initializeSharedPreferences(this);

        // записываем вопросы и ответы в файл (только один раз, если файл не создан ещё)
        String res = TaskFactory.writeQuestionsToFile(AppConstant.QUESTIONS_FILENAME, this);
        if (res.isEmpty()) {
            Log.d("DATA_RES", "Res: " + res);
        }

        // инициализация виджетов
        initWidget();

        // начальная инициализация данных игры
        firstInit();

        // записываем начальные значения виджетов
        restartWidget();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeUserGameData();  // сохраняем данные игры
    }


    public void clickAnswer(View view) {
        String tag = (String) view.getTag();

        if (tag == null) {
            return;
        }

        // если кнопка по которой кликнули это правильный ответ
        if (tag.equals(currQuestion.getAnswer())) {
            correctNumAnswer++;
            correctNumAnswerTextView.setText(String.valueOf(correctNumAnswer));
        }

        nextQuestion();
    }

    public void clickReset(View view) {
        writeUserGameData();  // сохраняем данные игры
        restartInit();
    }


    private void nextQuestion() {
        if (currNumQuestion >= AppConstant.COUNT_QUESTION) {
            restartInit();  // restart
            return;
        }

        currNumQuestion++;
        currNumQuestionTextView.setText(String.valueOf(currNumQuestion));

        currQuestion = questions.get(currNumQuestion - 1);
        questionTextView.setText(currQuestion.getQuestion());
    }

    private void writeUserGameData() {
        // записываем номер вопроса
        FileWork.writeIntToSharedPreferences(
                AppConstant.KEY_NUM_QUESTION_SHARED_PREFERENCES,
                currNumQuestion
        );

        // записываем кол-во правильных ответов
        FileWork.writeIntToSharedPreferences(
                AppConstant.KEY_NUM_CORRECT_ANSWER_SHARED_PREFERENCES,
                correctNumAnswer
        );
    }

    private void readUserGameData() {
        currNumQuestion = FileWork.readIntFromSharedPreferences(AppConstant.KEY_NUM_QUESTION_SHARED_PREFERENCES, 1);
        correctNumAnswer = FileWork.readIntFromSharedPreferences(AppConstant.KEY_NUM_CORRECT_ANSWER_SHARED_PREFERENCES, 0);
    }


    private void initWidget() {
        currNumQuestionTextView = findViewById(R.id.curr_num_question_text_view);
        questionTextView = findViewById(R.id.question_text_view);
        correctNumAnswerTextView = findViewById(R.id.correct_num_answer_text_view);
        countQuestionTextView = findViewById(R.id.count_question_text_view);
        correctBtn = findViewById(R.id.correct_btn);
        incorrectBtn = findViewById(R.id.incorrect_btn);

        // кнопкам записываем строку чтобы при клике их различать
        correctBtn.setTag(AppConstant.CORRECT_QUESTION);
        incorrectBtn.setTag(AppConstant.INCORRECT_QUESTION);
    }

    private void firstInit() {
        List<String> strQuestions = FileWork.readDataFromFile(AppConstant.QUESTIONS_FILENAME, this);
        questions = TaskFactory.convertStringsToQuestions(strQuestions);
        currQuestion = questions.get(0);
        readUserGameData();  // считываем данные игры
    }

    private void restartInit() {
        Collections.shuffle(questions);  // перемешаем вопросы
        currNumQuestion = 1;
        currQuestion = questions.get(0);
        correctNumAnswer = 0;
        restartWidget();  // записываем начальные значения виджетов
    }

    private void restartWidget() {
        currNumQuestionTextView.setText(String.valueOf(currNumQuestion));
        questionTextView.setText(currQuestion.getQuestion());
        countQuestionTextView.setText(String.valueOf(AppConstant.COUNT_QUESTION));
        correctNumAnswerTextView.setText(String.valueOf(correctNumAnswer));
    }
}