package command

import grails.validation.Validateable
import groovy.transform.ToString;

@Validateable
@ToString
class ListParams {
 
    public static final int MAX_DEFAULT = 10
    public static final int MAX_HIGH_LIMIT = 100
 
    Integer max = MAX_DEFAULT
    Integer offset
    String sort
    String order
 
    static constraints = {
        max(nullable: true, max: MAX_HIGH_LIMIT)
        offset(nullable: true, min: 0)
        sort(nullable: true, blank: false)
        order(nullable: true, blank: false, inList: ['asc', 'desc'])
    }
 
    Map <String, Object> getParams() {
        return [max: correctMax, offset: offset, sort: sort, order: order]
    }
 
    Integer getCorrectMax() {
        return Math.min(max ?: MAX_DEFAULT, MAX_HIGH_LIMIT)
    }
}
