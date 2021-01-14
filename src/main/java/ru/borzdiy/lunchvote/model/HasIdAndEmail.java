package ru.borzdiy.lunchvote.model;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}