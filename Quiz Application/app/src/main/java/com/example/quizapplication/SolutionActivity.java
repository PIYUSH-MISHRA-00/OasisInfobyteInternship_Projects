package com.example.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.immadisairaj.quiz.solution.SimpleFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolutionActivity extends AppCompatActivity {

    private ArrayList<Integer> answers;
    private ArrayList<Integer> answer;
    private ArrayList<String> question;
    private ArrayList<String> optA;
    private ArrayList<String> optB;
    private ArrayList<String> optC;
    private ArrayList<String> optD;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        ButterKnife.bind(this);

        answers = getIntent().getIntegerArrayListExtra("Answer");
        answer = getIntent().getIntegerArrayListExtra("Answers");
        question = getIntent().getStringArrayListExtra("Question");
        optA = getIntent().getStringArrayListExtra("optA");
        optB = getIntent().getStringArrayListExtra("optB");
        optC = getIntent().getStringArrayListExtra("optC");
        optD = getIntent().getStringArrayListExtra("optD");

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public ArrayList<Integer> getAnswers() {
        return answers;
    }

    public ArrayList<Integer> getAnswer() {
        return answer;
    }

    public ArrayList<String> getQuestion() {
        return question;
    }

    public ArrayList<String> getOptA() {
        return optA;
    }

    public ArrayList<String> getOptB() {
        return optB;
    }

    public ArrayList<String> getOptC() {
        return optC;
    }

    public ArrayList<String> getOptD() {
        return optD;
    }
}
