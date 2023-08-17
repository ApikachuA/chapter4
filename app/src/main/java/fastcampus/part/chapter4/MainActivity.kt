package fastcampus.part.chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import fastcampus.part.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener {
            //명시적 인텐트
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.emergencyContactLayer.setOnClickListener{
            // 암시적 인테트 -> 어떤 화면으로 가는 것이 아니라 어떤 액션을 하려고 함.
           with(Intent(Intent.ACTION_VIEW)){
                val phoneNumber = binding.emergencyContactValueTextView.text.toString().replace("-","")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataUiUpdate()
    }

    private fun getDataUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthdateValueTextView.text = getString(BIRTHDATE,"미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
            val warning = getString(WARNING,"")

            binding.CautionsValueTextView.isVisible = warning.isNullOrEmpty().not()
            binding.CautionsTextView.isVisible = warning.isNullOrEmpty().not()

            if(!warning.isNullOrBlank()){
                binding.CautionsValueTextView.text = warning
            }
            /* 반복되니까 위에처럼 만들 수 있음.
            if(warning.isNullOrBlank()){
                binding.CautionsValueTextView.isVisible = false
                binding.CautionsTextView.isVisible = false
            } else{
                binding.CautionsValueTextView.isVisible = true
                binding.CautionsTextView.isVisible = true
                binding.CautionsValueTextView.text = warning
            }
             */
        }
    }

    private fun deleteData(){
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataUiUpdate()
        }
        Toast.makeText(this, "초기화를 완료했습니다. ", Toast.LENGTH_SHORT).show()
    }

}