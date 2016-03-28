def index():
    if len(request.args)<1:
        raise HTTP(404)
    return request.args[0]

def get_noti():
    # return dict(notifcations=auth.user.username)
    noti = db(db.notifications.dest_user_id==auth.user.username).select(orderby=~db.notifications.time_stamp)
    db(db.notifications.dest_user_id==auth.user.username).update(seen=1)
    return dict(notifications=noti)

def set_noti_status():
    if auth.is_logged_in() and ("complaint_id" in request.vars) and ("response" in request.vars):
        db(db.notifications.dest_user_id==auth.user.username and db.notifications.complaint_id==request.vars.complaint_id).update(seen=int(request.vars.response))
        return dict(success= True)
    return dict(success=False)