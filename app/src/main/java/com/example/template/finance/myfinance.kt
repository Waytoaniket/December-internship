package com.example.template.finance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.widget.Spinner
import android.widget.TextView
import com.example.template.APICalls
import com.example.template.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.horizontal_recycler_of_date.view.*
import kotlinx.android.synthetic.main.updation_income.view.*
import com.xwray.groupie.GroupAdapter
import kotlinx.android.synthetic.main.activity_finance.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class myfinance : AppCompatActivity() {
    lateinit var option: Spinner
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)
        supportActionBar?.title="Category"

//        this is for button effect on button-all when it enter in
        button_all.setBackgroundResource(R.drawable.button_left_on_click)
        val textView=findViewById<TextView>(button_all.id)
        textView.setTextColor(Color.parseColor("#ffffff"))

//        this to get today date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())




        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val myresponse=APICalls().get("http://192.168.43.149:8000/cbo/payments/")
        Log.d("response11",myresponse)
        val data=JSONObject(myresponse)
        Log.d("response","${data.getString("data")}")
        val mydata = data.optJSONArray("data")?.let { 0.until(it.length()).map { i->it.optJSONObject(i)}}
            Log.d("response","$mydata")


        val adapter= GroupAdapter<GroupieViewHolder>()
        recycle_for_updation_of_income.adapter=adapter
        addALLitem(adapter,mydata)



        adapter.setOnItemClickListener { item, view ->
            val intent=Intent(view.context, Edit::class.java)
            startActivity(intent)
        }



        button_all.setOnClickListener {
            it.setBackgroundResource(R.drawable.button_left_on_click)
            val textView=findViewById<TextView>(button_all.id)
            textView.setTextColor(Color.parseColor("#ffffff"))
            val textView2=findViewById<TextView>(button_monthly.id)
            textView2.setTextColor(Color.parseColor("#009788"))
            val textView3=findViewById<TextView>(button_weekly.id)
            textView3.setTextColor(Color.parseColor("#009788"))
            val textView4=findViewById<TextView>(button_yearly.id)
            textView4.setTextColor(Color.parseColor("#009788"))
            button_monthly.setBackgroundResource(R.drawable.button_effect)
            button_weekly.setBackgroundResource(R.drawable.button_effect)
            button_yearly.setBackgroundResource(R.drawable.button_right_effect)
            adapter.clear()
            addALLitem(adapter,mydata)
        }
        button_weekly.setOnClickListener {
            it.setBackgroundResource(R.drawable.button_effect_on_click)
            val textView=findViewById<TextView>(button_weekly.id)
            textView.setTextColor(Color.parseColor("#ffffff"))
            val textView2=findViewById<TextView>(button_monthly.id)
            textView2.setTextColor(Color.parseColor("#009788"))
            val textView3=findViewById<TextView>(button_all.id)
            textView3.setTextColor(Color.parseColor("#009788"))
            val textView4=findViewById<TextView>(button_yearly.id)
            textView4.setTextColor(Color.parseColor("#009788"))
            button_all.setBackgroundResource(R.drawable.button_left_effect)
            button_monthly.setBackgroundResource(R.drawable.button_effect)
            button_yearly.setBackgroundResource(R.drawable.button_right_effect)
            adapter.clear()
            addTodayitem(adapter,mydata,currentDate)

        }
        button_monthly.setOnClickListener {
            it.setBackgroundResource(R.drawable.button_effect_on_click)
            val textView=findViewById<TextView>(button_monthly.id)
            textView.setTextColor(Color.parseColor("#ffffff"))
            val textView2=findViewById<TextView>(button_all.id)
            textView2.setTextColor(Color.parseColor("#009788"))
            val textView3=findViewById<TextView>(button_weekly.id)
            textView3.setTextColor(Color.parseColor("#009788"))
            val textView4=findViewById<TextView>(button_yearly.id)
            textView4.setTextColor(Color.parseColor("#009788"))
            button_all.setBackgroundResource(R.drawable.button_left_effect)
            button_yearly.setBackgroundResource(R.drawable.button_right_effect)
            button_weekly.setBackgroundResource(R.drawable.button_effect)
            layoutInflater.inflate(R.layout.spinner_of_month,layout_handel,false)

        }

        button_yearly.setOnClickListener {
            it.setBackgroundResource(R.drawable.button_right_effect_on_click)
            val textView=findViewById<TextView>(button_yearly.id)
            textView.setTextColor(Color.parseColor("#ffffff"))
            val textView2=findViewById<TextView>(button_monthly.id)
            textView2.setTextColor(Color.parseColor("#009788"))
            val textView3=findViewById<TextView>(button_weekly.id)
            textView3.setTextColor(Color.parseColor("#009788"))
            val textView4=findViewById<TextView>(button_all.id)
            textView4.setTextColor(Color.parseColor("#009788"))
            button_all.setBackgroundResource(R.drawable.button_left_effect)
            button_monthly.setBackgroundResource(R.drawable.button_effect)
            button_weekly.setBackgroundResource(R.drawable.button_effect)




        }
        Addition_of_income.setOnClickListener {
            Log.d("Newmessage","done")
            val intent =Intent(this, addition_of_income::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.nav_bar,menu)

        return super.onCreateOptionsMenu(menu)
    }


}
class UserItem(val Family_Id:Int,val username:String,val Money:Double,val Payment_Method:String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Log.d("NewMessage", "enter into user")
        viewHolder.itemView.Family_Id.text = Family_Id.toString()
        viewHolder.itemView.Amount_Paid.text = Money.toString()
        viewHolder.itemView.Payment_Method.text = Payment_Method
        viewHolder.itemView.Username.text = username

        Log.d("NewMessage", "")


    }

    override fun getLayout(): Int {
        return R.layout.updation_income
    }

}
class DateItem(val From_date:String, val to_date:String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Log.d("NewMessage", "enter into user")
        viewHolder.itemView.From_date.text = From_date
        viewHolder.itemView.To_date.text = to_date
        Log.d("NewMessage", "")

    }

    override fun getLayout(): Int {
        return R.layout.horizontal_recycler_of_date
    }

}
 fun addALLitem( adapter:GroupAdapter<GroupieViewHolder>,data:List<JSONObject>?){

    for (i in 0..(data!!.size-1)) {
         adapter.add(UserItem(data[i]["Family_id"] as Int, data[i]["fname"] as String, data[i]["Amount"] as Double, "Cash"))

     }
}
fun addTodayitem( adapter:GroupAdapter<GroupieViewHolder>,data:List<JSONObject>?,currentDate:String){

    for (i in 0..(data!!.size-1)) {
        val str = data[i]["timestamp"].toString()
        val separate1 = str.split("\\s".toRegex())[0]
        if (separate1==currentDate){
            adapter.add(UserItem(data[i]["Family_id"] as Int, data[i]["fname"] as String, data[i]["Amount"] as Double, "Cash"))
        }

    }
}



