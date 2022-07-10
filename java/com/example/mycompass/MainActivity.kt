package com.example.mycompass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.example.mycompass.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SensorEventListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: SensorManager
    var currentDegree: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //инициализируем manager
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    //Регистрируем manager
    override fun onResume() {
        super.onResume()
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME)

    }

    //Убираем регистрацию, чтобы при звонке или подобных действиях не тратить ресурсы
    override fun onPause() {
        super.onPause()
        manager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val degree: Int = p0?.values?.get(0)?.toInt()!!
        binding.tvDegree.text = degree.toString()

        val rotationAnim = RotateAnimation(currentDegree.toFloat(), (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        rotationAnim.duration = 210
        rotationAnim.fillAfter = true

        currentDegree = -degree

        binding.imgDynamic.startAnimation(rotationAnim)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}