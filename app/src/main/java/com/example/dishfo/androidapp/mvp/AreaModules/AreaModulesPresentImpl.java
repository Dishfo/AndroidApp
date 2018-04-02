package com.example.dishfo.androidapp.mvp.AreaModules;

import com.example.dishfo.androidapp.bean.viewBean.ViewArea;

/**
 *
 * Created by dishfo on 18-3-6.
 */

public class AreaModulesPresentImpl implements AreaModulesContract.AreaModulesPresent{

    private AreaModulesContract.AreaModulesModel model;
    private AreaModulesContract.AreaModulesView view;

    public AreaModulesPresentImpl(AreaModulesContract.AreaModulesModel model,
                                  AreaModulesContract.AreaModulesView view) {
        this.model = model;
        this.view = view;
        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        model.getAreaWithNotes((String) args[0]);
    }

    @Override
    public void stop() {
        model.stop();
    }

    @Override
    public void onCompete(Object... args) {
        view.compete(args[0],args[1]);
    }

    @Override
    public void onError(Object... args) {
        view.error(args[0]);
    }

    @Override
    public void onFollowArea(ViewArea viewArea) {
        model.FollowArea(viewArea);
    }
}
