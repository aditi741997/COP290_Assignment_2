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
    categories =db(db.complaint_category.category_id>=0).select()
    return dict(hostel_pref = hostpref, insti_pref = instipref, extra_pref = otherpref,categories=categories)

def update_preferences():
    hostelpref = (request.vars.hostel)
    instipref = (request.vars.institute)
    extrapref = request.vars.extra
    if not(len(hostelpref)==10) or not(len(instipref)==10) or not(len(extrapref)==10):
        return dict(success=False)
    db(db.users.username==auth.user.username).update(hostel_pref=hostelpref)
    db(db.users.username==auth.user.username).update(insti_pref=instipref)
    db(db.users.username==auth.user.username).update(extra_pref=extrapref)
    prefs =  db(db.users.username==auth.user.username).select()[0]
    hostpref = prefs["hostel_pref"]
    instipref = prefs["insti_pref"]
    otherpref = prefs["extra_pref"]
    return dict(success=True,hostelpref = hostpref, instipref = instipref, extrapref = otherpref)

