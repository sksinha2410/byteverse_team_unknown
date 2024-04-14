package com.sksinha2410.classmate.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.sksinha2410.classmate.Activities.AddUpdateActivity
import com.sksinha2410.classmate.Activities.UpcomingActivity
import com.sksinha2410.classmate.R
import com.sksinha2410.exploreease.Chatbot.MainActivity

class ResourceFragment : Fragment() {
    private lateinit var view: View
    private lateinit var btn1: CardView
    private lateinit var btn2: CardView
    private lateinit var btn3: CardView
    private lateinit var btn4: CardView
    private lateinit var btn5: CardView
    private lateinit var btn6: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resource, container, false)

        findByID()
        btn1.setOnClickListener{
            val intent: Intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        btn2.setOnClickListener{
            val intent: Intent = Intent(activity, UpcomingActivity::class.java)
            startActivity(intent)
        }
        btn3.setOnClickListener{
            val intent: Intent = Intent(activity, UpcomingActivity::class.java)
            startActivity(intent)
        }
        btn4.setOnClickListener{
            val intent: Intent = Intent(activity, UpcomingActivity::class.java)
            startActivity(intent)
        }
        btn5.setOnClickListener{
            val intent: Intent = Intent(activity, UpcomingActivity::class.java)
            startActivity(intent)
        }
        btn6.setOnClickListener{
            val intent: Intent = Intent(activity, UpcomingActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun findByID() {
        btn1= view.findViewById(R.id.cv_bot)
        btn2= view.findViewById(R.id.cv_syllabus)
        btn3= view.findViewById(R.id.cv_calender)
        btn4= view.findViewById(R.id.cv_ytVideo)
        btn5= view.findViewById(R.id.cv_pyq)
        btn6= view.findViewById(R.id.cv_extra)
    }
}