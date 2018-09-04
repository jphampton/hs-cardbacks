package com.jphampton.hearthstonecardbacks.service;

import com.google.common.base.Optional;
import com.jphampton.hearthstonecardbacks.models.Card;

import java.time.Month;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface HearthstoneService {
    Disposable getRankedCards(Consumer<List<Card>> onCompletion);

    Disposable getCardByDate(Month month, int year, Consumer<Optional<Card>> onCompletion);
}
