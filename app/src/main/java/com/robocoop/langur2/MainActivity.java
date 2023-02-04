package com.robocoop.langur2;

import android.os.Bundle;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);
    }

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        Locale locale = new Locale(Language.TURKISH, Region.TURKEY);

        Phrase phraseOK = new Phrase("Merhaba. Seni Anladım");
        Phrase phraseNOK = new Phrase("Seni Anlayamadım");

        Phrase phraseDinle = new Phrase("Merhaba. Artık Türkçe Konuşabiliyorum");

        // Create a new say action.
        Say sayOK = SayBuilder.with(qiContext) // Create the builder with the context.
                .withPhrase(phraseOK) // Set the text to say.
                .withLocale(locale)
                .build(); // Build the say action.

        Say sayNOK = SayBuilder.with(qiContext) // Create the builder with the context.
                .withPhrase(phraseOK) // Set the text to say.
                .withLocale(locale)
                .build(); // Build the say action.

        PhraseSet phraseSet = PhraseSetBuilder.with(qiContext)
                .withTexts("Merhaba")
                .build();

        Listen listen = ListenBuilder.with(qiContext)
                .withPhraseSet(phraseSet)
                .withLocale(locale).build();

        ListenResult listenResult = listen.run();

        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (matchedPhraseSet == phraseSet) {
            sayOK.run();
        }
        else
        {
            sayNOK.run();

        }


        // Execute the action.
        // say.run();

    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}