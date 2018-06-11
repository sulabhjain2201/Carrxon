package in.globalsoft.pojo;

import java.util.List;

/**
 * Created by root on 2/12/17.
 */

public class CountriesPojo extends BasePojo {

    private List<RegionPojo> region_list;



    public List<RegionPojo> getRegion_list() {
        return region_list;
    }

    public void setRegion_list(List<RegionPojo> region_list) {
        this.region_list = region_list;
    }
}
