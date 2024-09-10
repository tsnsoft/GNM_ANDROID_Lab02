package kz.talipovsn.quadratic;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Локальные переменные для доступа к компонентам окна
    private EditText editText_a, editText_b, editText_c, editText_x;
    private TextView textView_y, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация переменных доступа к компонентам окна
        editText_a = findViewById(R.id.editText_a);
        editText_b = findViewById(R.id.editText_b);
        editText_c = findViewById(R.id.editText_c);
        editText_x = findViewById(R.id.editText_x);
        textView_y = findViewById(R.id.textView_y);
        textView4 = findViewById(R.id.textView4);

        editText_x.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    int x = Integer.parseInt(String.valueOf(editText_x.getText()));
                    // Проверка на вводимое значение x и скрытие компонента editText_c
                    editText_c.setVisibility(x < 4 ? View.VISIBLE : View.GONE);
                } catch (Exception ee) {
                    // Установка видимости компонента editText_c
                    editText_c.setVisibility(View.GONE);
                }
                // Установка видимости компонента textView4
                textView4.setVisibility(editText_c.getVisibility());
                return false;
            }
        });

        // Проверка на переворот экрана и восстановление нужных значений в компонентах
        if (savedInstanceState != null) {
            textView_y.setText(savedInstanceState.getString("y")); // Восстановление значения y
            editText_c.setVisibility(savedInstanceState.getInt("v")); // Восстановление видимости компонента editText_c
            textView4.setVisibility(savedInstanceState.getInt("v")); // Восстановление видимости компонента textView4
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Сохранение нужных нам значений компонент при перевороте экрана
        outState.putString("y", textView_y.getText().toString()); // Сохранение значения y
        outState.putInt("v", editText_c.getVisibility()); // Сохранение видимости компонента editText_c
    }

    // МЕТОД ДЛЯ КНОПКИ РАСЧЕТА
    @SuppressLint("DefaultLocale")
    public void onCalc(View v) {
        // Локальные переменные
        double a, b, c = 0, y, x;
        try {
            // Считывание введенных входных значений в переменные
            a = Double.parseDouble(editText_a.getText().toString());
            b = Double.parseDouble(editText_b.getText().toString());
            x = Double.parseDouble(editText_x.getText().toString());
            if (editText_c.getVisibility() != View.GONE) {
                c = Double.parseDouble(editText_c.getText().toString());
            }
        } catch (Exception e) {
            // Выдача всплывающего сообщения на экран об ошибке
            Toast.makeText(MainActivity.this, "Неверные входные данные!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        // Расчет значений x и x2
        try {
            if (x < 4) {
                y = (3 * x + Math.pow(a, 2)) - c / (12 * b);
            } else {
                y = Math.pow(x, 1 / 5) * (7 * a - b);
            }
            if (Double.isNaN(y) || Double.isInfinite(y)) {
                throw new Exception();
            }
            textView_y.setText(String.format("y = %.3f", y));
        } catch (Exception e) {
            String err = "Нет решения!";
            textView_y.setText(err);
        }

    }


}
