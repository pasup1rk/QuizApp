package com.example.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by Kishore.P on 10/3/16.
 */

public class QuizActivity extends Activity implements OnClickListener {

	ArrayList < String > qustions_list = new ArrayList < String > (); {
		qustions_list.add("What is android?");
		qustions_list.add("What company developed android?");
		qustions_list.add("Android is based on which kernel?");
		qustions_list.add("Android is based on which language?");
		qustions_list.add("What is data base for android?");
	}
	ArrayList < ArrayList < String >> answers_list = new ArrayList < ArrayList < String >> (); {

		answers_list.add(new ArrayList < String > (Arrays.asList(
				"Desktop Operating System", "Programming Language",
				"Mobile Operating System", "Database",
				"Mobile Operating System")));
		answers_list.add(new ArrayList < String > (Arrays.asList("Apple", "Google",
				"Android Inc", "Nokia", "Android Inc")));
		answers_list.add(new ArrayList < String > (Arrays
				.asList("Linux kernel", "Windows kernel", "MAC kernel",
						"Hybrid Kernel", "Linux kernel")));
		answers_list.add(new ArrayList < String > (Arrays.asList("C", "C++",
				"VC++", "Java", "Java")));
		answers_list.add(new ArrayList < String > (Arrays.asList("my sql",
				"sql server", "sqlite", "oracle", "sqlite")));

	}
	HashMap < ArrayList < String > , ArrayList < ArrayList < String >>> quiz = new HashMap < ArrayList < String > , ArrayList < ArrayList < String >>> (); {
		quiz.put(qustions_list, answers_list);
	}

	List < HashMap < ArrayList < String > , ArrayList < ArrayList < String >>> > al = new ArrayList < HashMap < ArrayList < String > , ArrayList < ArrayList < String >>> > (); {
		al.add(quiz);
	}

	ArrayList < String > key = null;
	ArrayList < ArrayList < String >> value = null;
	ArrayList < String > answers;
	ArrayList < Integer > indexes = new ArrayList < Integer > ();
	ArrayList < String > selected_answers = new ArrayList < String > ();

	TextView ques, ques_no;

	int i = 0;
	Button btn_next, btn_previous, btn_option1, btn_option2, btn_option3,
			btn_option4;
	String result = "";
	String progress;
	int index;
	int btn_id;
	boolean btn_check = false;
	int score = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quiz);

		ques = (TextView) findViewById(R.id.textView_questions);
		ques_no = (TextView) findViewById(R.id.textView_qno);

		btn_next = (Button) findViewById(R.id.button_next);
		btn_previous = (Button) findViewById(R.id.button_previous);
		btn_option1 = (Button) findViewById(R.id.button_option1);
		btn_option2 = (Button) findViewById(R.id.button_option2);
		btn_option3 = (Button) findViewById(R.id.button_option3);
		btn_option4 = (Button) findViewById(R.id.button_option4);

		btn_next.setOnClickListener(this);
		btn_previous.setOnClickListener(this);
		btn_option1.setOnClickListener(this);
		btn_option2.setOnClickListener(this);
		btn_option3.setOnClickListener(this);
		btn_option4.setOnClickListener(this);

		btn_previous.setVisibility(View.INVISIBLE);
		getQuestions();
		ques_no.setText(String.valueOf(i + 1));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_next:
				if (indexes.size() > i) {
					btn_check = true;
				}
				String btn_data = btn_next.getText().toString();
				if (btn_data.equals("Finish")) {
					if (btn_check == false) {
						Toast.makeText(QuizActivity.this,
								"Please select the answer", Toast.LENGTH_SHORT)
								.show();
					} else {

						if (indexes.size() > i) {
							if (btn_id == 0) {
								int indx_btnid = indexes.get(i);
								indexes.set(i, indx_btnid);
							} else {
								indexes.set(i, btn_id);
							}
							selected_answers.set(i, result);
							btn_id = 0;
						} else {
							indexes.add(i, btn_id);
							selected_answers.add(i, result);
							btn_id = 0;
						}

						for (int i = 0; i < selected_answers.size(); i++) {
							answers = value.get(i);
							ques.setText(key.get(i));
							String correct_ans = answers.get(4);
							String select_ans = selected_answers.get(i);
							System.out.println("the correct ans:" + correct_ans + "  seclected ans:" + select_ans);

							if (correct_ans == select_ans) {
								score++;
								System.out.println("the score is" + score);
							} else {}
						}
						if (score < 3) {
							progress = "Please try again!";
						} else if (score == 3) {
							progress = "Good job!";
						} else if (score == 4) {
							progress = "Excellent work!";
						} else if (score == 5) {
							progress = "You are a genius!";
						}
						AlertDialog.Builder alert = new AlertDialog.Builder(
								QuizActivity.this);
						alert.setTitle("Result");
						alert.setMessage("Your Score is: " +
								String.valueOf(score) + " \n" + progress);
						alert.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										// TODO Auto-generated method stub

										dialog.dismiss();
										finish();
									}
								});
						AlertDialog dialog = alert.create();
						dialog.show();
					}
				} else {

					if (btn_check == false) {
						Toast.makeText(QuizActivity.this,
								"Please select the answer", Toast.LENGTH_SHORT)
								.show();
					} else {

						if (indexes.size() > i) {
							if (btn_id == 0) {
								int indx_btnid = indexes.get(i);
								indexes.set(i, indx_btnid);
								String res = selected_answers.get(i);
								selected_answers.set(i, res);
							} else {
								indexes.set(i, btn_id);
								selected_answers.set(i, result);
							}


							btn_id = 0;
							result = "";
						} else {
							indexes.add(i, btn_id);
							selected_answers.add(i, result);
							btn_id = 0;
							result = "";
						}



						if (i < key.size() - 1 && i >= 0) {
							i++;
							btn_previous.setVisibility(View.VISIBLE);
							getQuestions();
						}
						if (i == key.size() - 1) {
							btn_next.setText("Finish");
						}
						ques_no.setText(String.valueOf(i + 1));

						// result = "";

						if (indexes.size() > i) {
							int indx_val = indexes.get(i);
							if (indx_val == 1) {
								btn_option1
										.setBackgroundResource(R.drawable.customborder_select_button);
								btn_option2
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option3
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option4
										.setBackgroundResource(R.drawable.customborder_unselect_button);
							} else if (indx_val == 2) {
								btn_option1
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option2
										.setBackgroundResource(R.drawable.customborder_select_button);
								btn_option3
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option4
										.setBackgroundResource(R.drawable.customborder_unselect_button);
							} else if (indx_val == 3) {
								btn_option1
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option2
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option3
										.setBackgroundResource(R.drawable.customborder_select_button);
								btn_option4
										.setBackgroundResource(R.drawable.customborder_unselect_button);
							} else if (indx_val == 4) {
								btn_option1
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option2
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option3
										.setBackgroundResource(R.drawable.customborder_unselect_button);
								btn_option4
										.setBackgroundResource(R.drawable.customborder_select_button);
							}
						} else {

							btn_option1
									.setBackgroundResource(R.drawable.customborder_unselect_button);
							btn_option2
									.setBackgroundResource(R.drawable.customborder_unselect_button);
							btn_option3
									.setBackgroundResource(R.drawable.customborder_unselect_button);
							btn_option4
									.setBackgroundResource(R.drawable.customborder_unselect_button);
						}
					}
				}
				btn_check = false;
				break;
			case R.id.button_previous:
				if (i < key.size() && i > 0) {
					i--;
					getQuestions();
				}
				if (i == 0) {
					btn_previous.setVisibility(View.INVISIBLE);
				}
				if (i == key.size() - 2) {
					btn_next.setText("Next");
				}

				ques_no.setText(String.valueOf(i + 1));
				System.out.println("the indexes are:" + indexes);
				// result = "";
				int indx_val = indexes.get(i);
				if (indx_val == 1) {
					btn_option1
							.setBackgroundResource(R.drawable.customborder_select_button);
					btn_option2
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option3
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option4
							.setBackgroundResource(R.drawable.customborder_unselect_button);
				} else if (indx_val == 2) {
					btn_option1
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option2
							.setBackgroundResource(R.drawable.customborder_select_button);
					btn_option3
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option4
							.setBackgroundResource(R.drawable.customborder_unselect_button);
				} else if (indx_val == 3) {
					btn_option1
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option2
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option3
							.setBackgroundResource(R.drawable.customborder_select_button);
					btn_option4
							.setBackgroundResource(R.drawable.customborder_unselect_button);
				} else if (indx_val == 4) {
					btn_option1
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option2
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option3
							.setBackgroundResource(R.drawable.customborder_unselect_button);
					btn_option4
							.setBackgroundResource(R.drawable.customborder_select_button);
				}

				break;
			case R.id.button_option1:
				result = btn_option1.getText().toString();
				btn_id = 1;
				btn_check = true;
				btn_option1
						.setBackgroundResource(R.drawable.customborder_select_button);
				btn_option2
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option3
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option4
						.setBackgroundResource(R.drawable.customborder_unselect_button);

				break;
			case R.id.button_option2:
				result = btn_option2.getText().toString();
				btn_id = 2;
				btn_check = true;
				btn_option1
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option2
						.setBackgroundResource(R.drawable.customborder_select_button);
				btn_option3
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option4
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				break;
			case R.id.button_option3:
				result = btn_option3.getText().toString();
				btn_id = 3;
				btn_check = true;
				btn_option1
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option2
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option3
						.setBackgroundResource(R.drawable.customborder_select_button);
				btn_option4
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				break;
			case R.id.button_option4:
				result = btn_option4.getText().toString();
				btn_id = 4;
				btn_check = true;
				btn_option1
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option2
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option3
						.setBackgroundResource(R.drawable.customborder_unselect_button);
				btn_option4
						.setBackgroundResource(R.drawable.customborder_select_button);
				break;
			default:
				break;
		}
	}

	public void getQuestions() {

		for (HashMap < ArrayList < String > , ArrayList < ArrayList < String >>> entry: al) {

			for (Entry < ArrayList < String > , ArrayList < ArrayList < String >>> mapEntry: entry
					.entrySet()) {
				key = mapEntry.getKey();
				value = mapEntry.getValue();

				// index = new Random().nextInt(key.size());

			}
			System.out.println("the key size is:" + key.size());
			getanswers(i);

		}

	}

	public void getanswers(int pos) {

		answers = value.get(pos);
		ques.setText(key.get(pos));
		btn_option1.setText(answers.get(0));
		btn_option2.setText(answers.get(1));
		btn_option3.setText(answers.get(2));
		btn_option4.setText(answers.get(3));

	}


}