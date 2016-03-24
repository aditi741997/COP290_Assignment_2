def index():
    if len(request.args)<1:
        raise HTTP(404)
    return request.args[0]

def get_admin():
    comparea = request.vars.complain_area
    level = request.vars.level
    hostel = request.vars.Hostel
    prefs =  db(db.admin_info.complaint_area==comparea and db.admin_info.admin_level==level and db.admin_info.hostel_id==hostel).select()
    if len(prefs)==0:
    	prefs=[[]]
    return dict(admin = prefs[0])
