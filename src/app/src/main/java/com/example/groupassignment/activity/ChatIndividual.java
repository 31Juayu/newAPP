package com.example.groupassignment.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.groupassignment.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.google.firebase.database.GenericTypeIndicator;

/**
 * @author Tianyi Xu u7780366
 * @author Ruize Luo u7776709
 *
 */
public class ChatIndividual extends AppCompatActivity {
    private LinearLayout show_message_layout;
    private ScrollView scrollView;
    private EditText message_area;
    private DatabaseReference reference1, reference2;
    private String currentUserName, friendName;

    /**
     * This method is used to initialize the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call the super class to implement the onCreate
        super.onCreate(savedInstanceState);
        // set the content view to activity_chat_individual which is the chat room page
        setContentView(R.layout.activity_chat_individual);
        // call the initialize method of the view
        initializeViews();
        // configure the firebase database reference and get instance
        configureDatabaseReferences();
        // set the message sending
        setupMessageSending();
        // get the incoming message
        listenForIncomingMessages();
    }

    /**
     * Initialize the views and UI elements
     */
    private void initializeViews() {
        // initialzie the header, show messages area
        TextView chatHeader = findViewById(R.id.header_chat);
        friendName = getIntent().getStringExtra("friendName");
        currentUserName = getIntent().getStringExtra("currentUserName");
        chatHeader.setText(currentUserName + " chat with " + friendName);

        show_message_layout = findViewById(R.id.show_message_layout);
        scrollView = findViewById(R.id.scrollView);
        message_area = findViewById(R.id.send_Place);
    }

    /**
     * Configure the firebase database reference and get instances, the first reference is the current user_friend
     * The second reference is the friend_current user
     */
    private void configureDatabaseReferences() {
        String toUseUserName = sanitize(currentUserName);
        String toUseFriendName = sanitize(friendName);
        reference1 = FirebaseDatabase.getInstance().getReference("messages/" + toUseUserName + "_" + toUseFriendName);
        reference2 = FirebaseDatabase.getInstance().getReference("messages/" + toUseFriendName + "_" + toUseUserName);
    }

    /**
     * Since the firebase database does not allow to have "." in the names, replace them with "-"
     * @param userName
     * @return
     */
    private String sanitize(String userName) {
        return userName.replace(".", "-");
    }

    /**
     * Set up the message sending function
     */
    private void setupMessageSending() {
        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> {
            String message = message_area.getText().toString();
            if (!message.isEmpty()) {
                sendMessage(message);
                message_area.setText("");
            }
        });
    }

    /**
     * send a message to the database
     * @param message
     */
    private void sendMessage(String message) {
        // Use a map to store the current message and user information
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("current_message", message);
        messageMap.put("current_user", currentUserName);
        // References to both user's message paths in the database
        DatabaseReference[] references = {reference1, reference2};
        // push the message to both database reference
        for (DatabaseReference ref : references) {
            ref.push().setValue(messageMap)
                    // show success message
                    .addOnSuccessListener(aVoid -> Toast.makeText(ChatIndividual.this, "Message sent successfully!", Toast.LENGTH_SHORT).show())
                    // show failure message
                    .addOnFailureListener(e -> Toast.makeText(ChatIndividual.this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    /**
     * Get the incoming message from the database
     */
    private void listenForIncomingMessages() {
        // add a child event listener to the database reference
        reference1.addChildEventListener(new ChildEventListenerAdapter() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Map<String, String> msg = snapshot.getValue(Map.class);
                // Use a generic type indicator to retrieve the message map
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                Map<String, String> msg= snapshot.getValue(genericTypeIndicator);
                // Display the message, indicating if it was sent by the current user
                displayMessage(Objects.requireNonNull(msg), currentUserName.equals(msg.get("current_user")));
            }
        });
    }

    /**
     * Display a chat message
     * @param msg
     * @param isCurrentUser
     */
    private void displayMessage(Map<String, String> msg, boolean isCurrentUser) {
        // Determine the format of the message
        String sender = isCurrentUser ? "You" : friendName;
        messageViewer(sender + ":\n" + msg.get("current_message"), isCurrentUser ? 1 : 0);
    }

    /**
     * Add a message to the chat view
     * @param message
     * @param userType
     */
    private void messageViewer(String message, int userType) {
        // create a TextView to show the messages
        TextView textView = new TextView(this);
        textView.setText(message);
        // set layout parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(layoutParams);
        // set the background
        textView.setBackgroundResource(userType == 1 ? R.drawable.message1 : R.drawable.message0);
        // add to the scroll view
        show_message_layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    /**
     * simple adapter
     */
    private static class ChildEventListenerAdapter implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {}
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {}
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    }
}
