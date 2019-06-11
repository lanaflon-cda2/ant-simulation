package com.wilcoln;

import com.wilcoln.utils.Time;

public interface AnimalEnvironmentView {
    void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt);
    void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt);
}
