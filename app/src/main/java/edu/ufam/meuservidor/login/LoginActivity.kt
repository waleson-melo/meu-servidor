package edu.ufam.meuservidor.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import edu.ufam.meuservidor.MainActivity
import edu.ufam.meuservidor.R

class LoginActivity : AppCompatActivity() {
    var gop: GoogleSignInOptions? = null
    var goc: GoogleSignInClient? = null

///ImageView googleBtn;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        gop =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        goc = GoogleSignIn.getClient(this, gop!!)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            finish()
            val i = Intent(
                this@LoginActivity,
                MainActivity::class.java
            )
            startActivity(i)
        }
        val googleBtn: ImageButton? = findViewById(R.id.btn_login_google)
        if (googleBtn != null) {
            googleBtn.setOnClickListener(View.OnClickListener { signIn() })
        }
    }

    fun signIn() {
        val signInIntent = goc!!.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(
                    applicationContext,
                    "Algo deu errado. Tente novamente!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun navigateToSecondActivity() {
        finish()
        val i = Intent(
            this@LoginActivity,
            MainActivity::class.java
        )
        startActivity(i)
    }

}