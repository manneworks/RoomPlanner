package roomplanner.dao

import persistence.XStreamSolutionDao;
import roomplanner.Schedule;

public class ScheduleDao extends XStreamSolutionDao {

    public ScheduleDao() {
        super("benchmark/data", Schedule.class);
    }

}
