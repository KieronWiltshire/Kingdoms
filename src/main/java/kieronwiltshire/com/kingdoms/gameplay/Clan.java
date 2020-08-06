package kieronwiltshire.com.kingdoms.gameplay;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clans")
public class Clan extends Model {

    @Id
    Long id;

}
