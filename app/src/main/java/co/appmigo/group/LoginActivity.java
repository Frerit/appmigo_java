package co.appmigo.group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import co.appmigo.group.common.User;
import co.appmigo.group.common.Util;
import co.appmigo.group.module.MainActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private Button btnLoginPress;
    private FirebaseAuth mAuth;
    private String password;
    InputMethodManager imm;
    private String email;
    private User user;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }


    private void initView() {
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tietEmail = findViewById(R.id.tietEmail);
        tietPassword = findViewById(R.id.tietPassword);
        btnLoginPress = findViewById(R.id.loginPresed);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("!Fire", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("!Fire", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void initListener() {
        btnLoginPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appAtenpiLoginPress();
            }
        });
        tietPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (id == EditorInfo.IME_ACTION_DONE)) {
                    appAtenpiLoginPress();
                    return true;
                }
                return false;
            }
        });
    }

    private void appAtenpiLoginPress() {
        if (validate()) {
            Log.e("||||", "Entra a validar");
            User userForm = new User();
            userForm.setEmail(tietEmail.getText().toString().trim());
            userForm.setPassword(tietPassword.getText().toString().trim());
            loginFIrebase(userForm);
        } else {
            view = this.getCurrentFocus();
            if (view != null) {
                hideSoftKeyBoard();
            }
        }
    }

    private void loginFIrebase(User user) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("|Firebase", "Logeado");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user",user.getUid());

                    LoginActivity.this.startActivity(intent);

                } else {
                    Log.w("||Firebase", "Login:failed", task.getException());
                    Toast.makeText(LoginActivity.this, "Autenticacion Failed",Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if ( user != null ) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user",user.getUid());

            LoginActivity.this.startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // Actualiza el usuario cuando la app inicie
    private void updateUI(FirebaseUser currentUser) {
        Log.d("|Firebase", "User: ");
        if (currentUser != null) {
            user = new User(currentUser);
        } else {
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setTitle("Upps...");
               builder.setMessage("No se logro conexion con el servidor Intenta nuevamente");
               builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       tietEmail.setText("");
                       tietPassword.setText("");
                   }
               });
             builder.create();
        }
    }

    @Override  // Cierra la app
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean validate() {
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilEmail.setErrorEnabled(true);
        tilPassword.setErrorEnabled(true);

        if ( tietEmail.getText().toString().length() == 0) {
            tilEmail.setError("Upp.. falta el correo");
            hideSoftKeyBoard();
            return false;
        }

        if (!Util.isValidEmail(tietEmail.getText())) {
            tilEmail.setError("Creo que algo le falta el correo");
            hideSoftKeyBoard();
            return false;
        }

        if ( tietPassword.getText().toString().length() == 0) {
            tilPassword.setError("Upp.. falta el correo");
            hideSoftKeyBoard();
            return false;
        }
        return true;
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
