/*
 * Copyright (c) 2016 One by Aol : Publishers. All rights reserved.
 */

package com.aol.mobile.sdk.controls.utils;

import android.support.annotation.NonNull;

import com.aol.mobile.sdk.controls.view.PlayerControlsView;

public final class VisibilityModule {
    @NonNull
    private final PlayerControlsView playerControlsView;
    @NonNull
    private Behaviour behaviour;
    private final Behaviour HIDDEN_PLAYING = new BehaviourAdapter() {
        @Override
        public void pause() {
            playerControlsView.cancelTimer();
            playerControlsView.show();
            behaviour = VISIBLE_PAUSED;
        }

        @Override
        public void tap() {
            playerControlsView.startTimer();
            playerControlsView.show();
            behaviour = VISIBLE_PLAYING;
        }
    };
    private final Behaviour VISIBLE_PLAYING = new BehaviourAdapter() {
        @Override
        public void pause() {
            playerControlsView.cancelTimer();
            playerControlsView.show();
            behaviour = VISIBLE_PAUSED;
        }

        @Override
        public void tap() {
            playerControlsView.cancelTimer();
            playerControlsView.hide();
            behaviour = HIDDEN_PLAYING;
        }

        @Override
        public void timeout() {
            playerControlsView.hide();
            behaviour = HIDDEN_PLAYING;
        }

        @Override
        public void prolong() {
            playerControlsView.cancelTimer();
            playerControlsView.startTimer();
        }
    };
    private final Behaviour HIDDEN_PAUSED = new BehaviourAdapter() {
        @Override
        public void play() {
            playerControlsView.startTimer();
            playerControlsView.show();
            behaviour = VISIBLE_PLAYING;
        }

        @Override
        public void tap() {
            playerControlsView.cancelTimer();
            playerControlsView.show();
            behaviour = VISIBLE_PAUSED;
        }
    };
    private final Behaviour VISIBLE_PAUSED = new BehaviourAdapter() {
        @Override
        public void play() {
            playerControlsView.startTimer();
            playerControlsView.show();
            behaviour = VISIBLE_PLAYING;
        }

        @Override
        public void tap() {
            playerControlsView.cancelTimer();
            playerControlsView.hide();
            behaviour = HIDDEN_PAUSED;
        }
    };

    public VisibilityModule(@NonNull PlayerControlsView playerControlsView) {
        this.playerControlsView = playerControlsView;
        this.behaviour = VISIBLE_PAUSED;
    }

    public void play() {
        behaviour.play();
    }

    public void pause() {
        behaviour.pause();
    }

    public void tap() {
        behaviour.tap();
    }

    public void timeout() {
        behaviour.timeout();
    }

    public void prolong() {
        behaviour.prolong();
    }

    private interface Behaviour {
        void play();

        void pause();

        void tap();

        void timeout();

        void prolong();
    }

    private class BehaviourAdapter implements Behaviour {
        @Override
        public void play() {
        }

        @Override
        public void pause() {
        }

        @Override
        public void tap() {
        }

        @Override
        public void timeout() {
        }

        @Override
        public void prolong() {
        }
    }
}
