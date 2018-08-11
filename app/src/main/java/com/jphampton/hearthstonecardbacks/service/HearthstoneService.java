package com.jphampton.hearthstonecardbacks.service;

import com.jphampton.hearthstonecardbacks.models.Card;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface HearthstoneService {
    Disposable getRankedCards(Consumer<List<Card>> onCompletion);

}
