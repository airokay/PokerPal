package givememoney.pokerpal;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import givememoney.table.Game;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

//Make BET CHECK FOLD function
//Make PLAYER LIST dialog between Main menu and pokergame

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startPokerGame(View v) {
        Intent intent = new Intent(this, ChooseGameOptions.class);

        //TODO: Ask user for Game starting parameters, then post the Game with user params
        //onto bus for pokergame to collect later.

        //getUserGameSettings()
        //EventBus.getDefault().postSticky(new Game(10));
        startActivity(intent);



    }

}
