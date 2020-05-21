package com.example.meeatnow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_output = findViewById(R.id.tv_output);
        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int[] input = new int[]{3};
                int[] output = new int[]{0};

                Interpreter tflite = getTfliteInterpreter("simple_1.tflite");

                assert tflite != null;
                tflite.run(input, output);

                tv_output.setText(String.valueOf(output[0]));
            }
        });

        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[][] input = new int[][]{{3}, {7}};
                int[] output = new int[]{0};

                Interpreter tflite = getTfliteInterpreter("simple_2.tflite");
                assert tflite != null;
                tflite.run(input, output);

                tv_output.setText(String.valueOf(output[0]));
            }
        });

        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] input_1 = new int[]{3};
                int[] input_2 = new int[]{7};
                int[][] inputs = new int[][]{input_1, input_2};

                // 출력은 하나지만, 함수 매개변수를 맞추기 위해 맵 생성
                java.util.Map<Integer, Object> outputs = new java.util.HashMap<>();

                // 출력을 받아올 변수 1개 추가
                int[] output_1 = new int[]{0};
                outputs.put(0, output_1);

                Interpreter tflite = getTfliteInterpreter("simple_3.tflite");

                // 입력은 입력들의 배열, 출력은 <Integer, Object> 형태의 Map.
                // key와 value에 해당하는 Integer와 Object 자료형은 변경할 수 없다.
                assert tflite != null;
                tflite.runForMultipleInputsOutputs(inputs, outputs);

                tv_output.setText(String.valueOf(output_1[0]));
            }
        });

        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                int[][] inputs = new int[][]{{3}, {7}};

                HashMap outputs = new HashMap();

                // 별도 변수 없이 직접 put 함수에 전달하면서 배열 생성
                outputs.put(0, new int[]{0});
                outputs.put(1, new int[]{0});

                Interpreter tflite = getTfliteInterpreter("simple_4.tflite");
                assert tflite != null;
                tflite.runForMultipleInputsOutputs(inputs, outputs);

                // 별도로 출력 변수를 정의하지 않았기 때문에 Map 클래스의 get 함수를 통해 가져온다.
                // Object 자료형을 배열로 변환해서 사용
                int[] output_1 = (int[]) outputs.get(0);
                int[] output_2 = (int[]) outputs.get(1);
                assert output_1 != null;
                assert output_2 != null;
                tv_output.setText(output_1[0] + " : " + output_2[0]);
            }
        });
    }

    // 모델 파일 인터프리터를 생성하는 공통 함수
    // loadModelFile 함수에 예외가 포함되어 있기 때문에 반드시 try, catch 블록이 필요하다.
    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(MainActivity.this, modelPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // MappedByteBuffer 바이트 버퍼를 Interpreter 객체에 전달하면 모델 해석을 할 수 있다.
    // tensorflow lite 홈페이지 참고
    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}



