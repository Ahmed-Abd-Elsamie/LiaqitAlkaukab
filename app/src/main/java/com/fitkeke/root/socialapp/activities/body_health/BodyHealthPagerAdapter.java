package com.fitkeke.root.socialapp.activities.body_health;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitkeke.root.socialapp.fragments.body_health_tabs.ArticlesFragment;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.CloriesFragment;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.RecipeFragment;
import com.fitkeke.root.socialapp.fragments.body_health_tabs.SuppFragment;


public class BodyHealthPagerAdapter extends FragmentPagerAdapter {


    public BodyHealthPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:

                RecipeFragment recipeFragment = new RecipeFragment();
                return recipeFragment;

            case 1:

                ArticlesFragment articlesFragment = new ArticlesFragment();
                return articlesFragment;

            case 2:

                SuppFragment suppFragment = new SuppFragment();
                return suppFragment;

            case 3:

                CloriesFragment cloriesFragment = new CloriesFragment();
                return cloriesFragment;

            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return 4;
    }
}