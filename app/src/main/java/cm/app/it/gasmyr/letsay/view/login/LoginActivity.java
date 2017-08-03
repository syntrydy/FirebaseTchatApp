package cm.app.it.gasmyr.letsay.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import cm.app.it.gasmyr.letsay.R;
import cm.app.it.gasmyr.letsay.core.User;
import cm.app.it.gasmyr.letsay.core.app.LetsayApp;
import cm.app.it.gasmyr.letsay.firebase.Tchat;
import cm.app.it.gasmyr.letsay.util.AppConstants;
import cm.app.it.gasmyr.letsay.view.discussion.DiscussionListActivity;
import cm.app.it.gasmyr.letsay.view.user.UserListActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final String TAG = "LETSAY";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    private EditText mNameField;
    private EditText mPhoneNumberField;
    private EditText mVerificationCodeField;

    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;

    private TextView mStatusText;
    private TextView mDetailText;
    private User currentUser = new User();
    private String currentPhone;
    private String currentName;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        setup();
        initViews();
        setupFirebase();
    }

    private void setupFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                currentUser.setName(currentName);
                currentUser.setPhone(currentPhone);
                currentUser.setOnline(true);
                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;
                hideProgressDialog();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhoneNumberField.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                hideProgressDialog();
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(STATE_CODE_SENT);
            }
        };
    }

    private void setup() {
        sharedPreferences=getSharedPreferences(AppConstants.SHARE_PREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        LetsayApp.setContext(LoginActivity.this);
    }

    private void initViews() {
        mNameField = (EditText) findViewById(R.id.userName);
        mPhoneNumberField = (EditText) findViewById(R.id.phoneNumber);
        mVerificationCodeField = (EditText) findViewById(R.id.field_verification_code);

        mStartButton = (Button) findViewById(R.id.button_start_verification);
        mVerifyButton = (Button) findViewById(R.id.button_verify_phone);
        mResendButton = (Button) findViewById(R.id.button_resend);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);

        mStatusText = (TextView) findViewById(R.id.status);
        mDetailText = (TextView) findViewById(R.id.detail);

        mStartButton.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
        mResendButton.setOnClickListener(this);
        mSignOutButton.setOnClickListener(this);
    }


    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            hideProgressDialog();
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mVerificationCodeField.setError("Invalid code.");
                            }
                            hideProgressDialog();
                            updateUI(STATE_SIGNIN_FAILED);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                enableViews(mStartButton, mPhoneNumberField);
                disableViews(mVerifyButton, mResendButton, mVerificationCodeField);
                mDetailText.setText(null);
                break;
            case STATE_CODE_SENT:
                enableViews(mVerifyButton, mResendButton, mVerificationCodeField);
                disableViews(mStartButton, mPhoneNumberField,mNameField);
                mDetailText.setText("Code envoyé");
                break;
            case STATE_VERIFY_FAILED:
                enableViews(mStartButton, mPhoneNumberField);
                mDetailText.setText("Echec de verification");
                disableViews(mVerifyButton, mResendButton, mVerificationCodeField,mNameField);
                break;
            case STATE_VERIFY_SUCCESS:
                disableViews(mStartButton, mResendButton, mPhoneNumberField,mNameField);
                mDetailText.setText("Vérification confirmée");
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mVerificationCodeField.setText(cred.getSmsCode());
                    } else {
                        mVerificationCodeField.setText("Vérification instantanée");
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                mDetailText.setText("échec d'authentification");
                break;
            case STATE_SIGNIN_SUCCESS:
                editor.putString(AppConstants.USERID,currentUser.getPhone());
                editor.commit();
                Tchat.registerNewUser(currentUser);
                goToDiscussionsList();
                break;
        }

        if (user == null) {
            mStatusText.setText("deconnecté");
            ;
        } else {
            enableViews(mPhoneNumberField, mVerificationCodeField);
            mPhoneNumberField.setText(null);
            mVerificationCodeField.setText(null);
            mStatusText.setText("Online");
            mDetailText.setText("Firebase connexion On");
        }
    }

    private void goToDiscussionsList() {
        Intent intent = new Intent(LoginActivity.this, DiscussionListActivity.class);
        startActivity(intent);
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(mNameField.getText().toString())) {
            mPhoneNumberField.setError("Numéro invalid.");
            return false;
        }
        return true;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_verification:
                if (!validatePhoneNumber()) {
                    return;
                }
                showProgressDialog();
                currentPhone = mPhoneNumberField.getText().toString();
                currentName=mNameField.getText().toString();
                startPhoneNumberVerification(mPhoneNumberField.getText().toString());
                break;
            case R.id.button_verify_phone:
                showProgressDialog();
                String code = mVerificationCodeField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationCodeField.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:
                showProgressDialog();
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
            case R.id.sign_out_button:
                //signOut();
                goToDiscussionsList();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

}

