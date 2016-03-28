def index():
	if len(request.args)<1:
		raise HTTP(404)
	return request.args[0]

def preferences():
    if (auth.user==None):
        return dict(Success="false")
    prefs =  db(db.users.username==auth.user.username).select()[0]
    hostpref = prefs["hostel_pref"]
    instipref = prefs["insti_pref"]
    otherpref = prefs["extra_pref"]
    # TODO: Add description
    return dict(hostel_prefs = hostpref, insti_pref = instipref, extra_pref = otherpref)

def update_preferences():
    hostelpref = request.vars.hostel
    instipref = request.vars.institute
    extrapref = request.vars.extra
    db(db.users.username==auth.user.username).update(hostel_pref=hostelpref)
    db(db.users.username==auth.user.username).update(insti_pref=instipref)
    db(db.users.username==auth.user.username).update(extra_pref=extrapref)
    prefs =  db(db.users.username==auth.user.username).select()[0]
    hostpref = prefs["hostel_pref"]
    instipref = prefs["insti_pref"]
    otherpref = prefs["extra_pref"]
    # db(db.notifications.dest_user_id==auth.user.username).update(seen=1)
    # return dict(hos = prefs)
    return dict(hostel_prefs = hostpref, insti_pref = instipref, extra_pref = otherpref)

