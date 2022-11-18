package com.example.finalproject.service;

import com.example.finalproject.model.Advertisement;

import java.util.List;

public interface IAdvertisementService {
    /**
     * Find all advertisements.
     *
     * @return A list of all the advertisements in the database.
     */
    List<Advertisement> findAll();

    /**
     * Find all advertisements that have a category that matches the given category.
     *
     * @param category The category of the advertisement.
     * @return List of all advertisements that have the same category as the one passed in.
     */
    List<Advertisement> findAllByCategory(String category);
}
