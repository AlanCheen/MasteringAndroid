package me.yifeiyuan.masteringandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.yifeiyuan.flap.FlapAdapter
import me.yifeiyuan.flap.FlapComponent
import me.yifeiyuan.flap.annotations.Component

open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showcaseAdapter = FlapAdapter().apply {

            val showcases = ArrayList<Showcase>()

            showcases.add(Showcase("Activity", View.OnClickListener {
                start("me.yifeiyuan.mactivity.MasteringActivityActivity")
            }))

            this.data = showcases
        }

        list.adapter = showcaseAdapter
    }

    data class Showcase(val txt: String, val clickListener: View.OnClickListener)

    @Component(layoutId = R.layout.showcase_item, autoRegister = true)
    open class ShowcaseComponent(view: View) : FlapComponent<Showcase>(view) {

        private var text: TextView

        init {
            text = findViewById<TextView>(R.id.showcaseText)
        }

        override fun onBind(model: Showcase) {
            text.text = model.txt
            itemView.setOnClickListener(model.clickListener)
        }

    }

    fun start(activityClassName: String) {
        start(Intent().apply {
            setClassName(this@MainActivity, activityClassName)
        })
    }

    private fun start(intent: Intent) {
        intent.resolveActivity(packageManager)?.let {
            startActivity(intent)
        }
    }
}