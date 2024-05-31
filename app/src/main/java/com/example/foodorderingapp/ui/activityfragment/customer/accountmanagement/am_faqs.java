package com.example.foodorderingapp.ui.activityfragment.customer.accountmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapp.R;

public class am_faqs extends AppCompatActivity {
    FrameLayout btnBack;
    TextView tv_qs1, tv_qs2, tv_qs3, tv_qs4, tv_qs5;
    TextView tv_ans1, tv_ans2, tv_ans3, tv_ans4, tv_ans5;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_am_faqs);

        TextView headerName = findViewById(R.id.screen_name);
        headerName.setText("Câu hỏi thường gặp");

        // set button back click event
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_qs1 = findViewById(R.id.tv_qs1);
        tv_qs2 = findViewById(R.id.tv_qs2);
        tv_qs3 = findViewById(R.id.tv_qs3);
        tv_qs4 = findViewById(R.id.tv_qs4);
        tv_qs5 = findViewById(R.id.tv_qs5);

        tv_ans1 = findViewById(R.id.tv_ans1);
        tv_ans2 = findViewById(R.id.tv_ans2);
        tv_ans3 = findViewById(R.id.tv_ans3);
        tv_ans4 = findViewById(R.id.tv_ans4);
        tv_ans5 = findViewById(R.id.tv_ans5);

        tv_qs1.setText("Tôi gặp sự cố với ứng dụng, tôi phải làm gì?");
        tv_ans1.setText("Nếu bạn gặp bất kỳ sự cố nào với ứng dụng, vui lòng liên hệ với bộ phận hỗ trợ khách hàng qua email hoặc số điện thoại hỗ trợ. Chúng tôi sẽ phản hồi và giúp đỡ bạn trong thời gian sớm nhất.");

        tv_qs2.setText("Làm thế nào để đánh giá và phản hồi về đơn hàng?");
        tv_ans2.setText("Sau khi đơn hàng được giao, bạn sẽ nhận được thông báo yêu cầu đánh giá đơn hàng. Bạn có thể vào mục \"Đơn hàng của tôi\" và chọn đơn hàng mà bạn muốn đánh giá. Sau đó, bạn có thể để lại đánh giá và phản hồi của mình.");

        tv_qs3.setText("Tôi có thể hủy đơn hàng sau khi đã đặt không?");
        tv_ans3.setText("Bạn có thể hủy đơn hàng nếu đơn hàng chưa được xác nhận. Để làm điều này, vào mục \"Đơn hàng của tôi\", chọn đơn hàng bạn muốn hủy và làm theo hướng dẫn. Nếu đơn hàng đã được xử lý, bạn cần liên hệ trực tiếp với cửa hàng để được hỗ trợ.");

        tv_qs4.setText("Làm thế nào để theo dõi tình trạng đơn hàng của tôi?");
        tv_ans4.setText("Sau khi đặt hàng, bạn có thể theo dõi tình trạng đơn hàng của mình bằng cách vào mục \"Đơn hàng của tôi\" trong ứng dụng. Tại đây, bạn sẽ thấy các thông tin chi tiết về đơn hàng, trạng thái đơn hàng hiện tại.");

        tv_qs5.setText("Tôi có thể sử dụng ứng dụng ở những khu vực nào?");
        tv_ans5.setText("Ứng dụng hiện hỗ trợ đặt hàng tại các khu vực cụ thể mà cửa hàng cà phê của chúng tôi có mặt. Vui lòng kiểm tra danh sách các khu vực hỗ trợ trên ứng dụng hoặc liên hệ với cửa hàng để biết thêm chi tiết.");
    }
}