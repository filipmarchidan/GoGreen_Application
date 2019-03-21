package API;

import database.ActivityRepository;
import database.UserRepository;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

}
