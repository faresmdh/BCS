package m.ify.computersciencebouira.Activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.ActivityLevelBinding

class ActivityLevel : AppCompatActivity() {

    private lateinit var b:ActivityLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(b.root)



        b.startBtn.setOnClickListener {
            val level = when(b.chipGroup.checkedChipId){
                R.id.l1 -> "L1 Informatique"
                R.id.l2 -> "L2 Informatique"
                R.id.l3 -> "L3 Systemes informatique"
                else -> "L3 Systemes informatique"
            }
            StateSaver(this).setText("level",level)
            val i = Intent(this@ActivityLevel,ActivityMain::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }


    }
}