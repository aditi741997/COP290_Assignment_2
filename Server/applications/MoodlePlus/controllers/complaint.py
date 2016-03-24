def index():
    if len(request.args)<1:
        raise HTTP(404)
    return request.args[0]

def get_user_response():
    if auth.is_logged_in() and ("complain_id" in request.vars):
        complaint_id = request.vars.complain_id
        prefs =  db(db.complaint_response.complaint_id==complaint_id and db.complaint_response.username==auth.user.username).select()
        if len(prefs)==0:
            prefs=0
        else:
            prefs = prefs[0]['response']
        return dict(resp = prefs)
    else:
        return dict(Success=False)

def set_user_response():
    if auth.is_logged_in() and ("complain_id" in request.vars) and ("response" in request.vars):
        complaint_id = request.vars.complain_id
        resp = int(request.vars.response)
        prefs =  db(db.complaint_response.complaint_id==complaint_id and db.complaint_response.username==auth.user.username).select()
        if len(prefs)==0:
            db.complaint_response.insert(username=auth.user.username, complaint_id=complaint_id, response=resp)
            # prefs=0
        else:
            # db.complaint_response.update()
            db(db.complaint_response.username==auth.user.username and db.complaint_response.complaint_id==complaint_id).update(response=resp)
        return dict(Success = True)
    else:
        return dict(Success=False)

def get_desc():
    if ("category_id" in request.vars):
        category_id = int(request.vars.category_id)
        pref=None
        if category_id>=0:
            pref =  db(db.complaint_category.category_id==category_id).select()
        else:
            pref = db(db.complaint_category.category_id>=0).select()
        return dict(Categories=pref)
    else:
        return dict(Categories = False)

def get_comments():
    ans=[]
    if ("complain_id" in request.vars and auth.is_logged_in()):
        complain_id=request.vars.complain_id
        pref=db(db.comments_complaint.complaint_id==complain_id).select()
        # ans =[]
        for elem in pref:
            ans.append(elem)
    return dict(Comments = ans)


def post_comments():
    if ("complain_id" in request.vars and "comment" in request.vars and auth.is_logged_in()):
        complain_id = request.vars.complain_id
        comment = request.vars.comment
        anony=0 
        if "anon" in request.vars:
            anony = int(request.vars.anon)
        db.comments_complaint.insert(
            complaint_id=complain_id,
            user_id = auth.user.username,
            description=comment,
            time_stamp=datetime.now,
            anonymous=anony
            )
        return dict(Success = True)
    return dict(Success = False)

def get_user_status():
    if ("complaint_id" in request.vars and auth.is_logged_in()):
        pref=db(db.user_satisfaction_response.username==auth.user.username and db.user_satisfaction_response.complaint_id==request.vars.complaint_id).select()
        resp=0
        if len(pref)>0:
            resp=pref[0]["response"]
        return dict(Status=resp)
    return dict(Status = 0)

def put_user_status():
    if ("complaint_id" in request.vars and "response" in request.vars and auth.is_logged_in()):
        pref=db(db.user_satisfaction_response.username==auth.user.username and db.user_satisfaction_response.complaint_id==request.vars.complaint_id).select()
        resp=0
        if len(pref)>0:
            db(db.user_satisfaction_response.username==auth.user.username and db.user_satisfaction_response.complaint_id==request.vars.complaint_id).update(response=request.vars.response)
        else:
            db.user_satisfaction_response.insert(username=auth.user.username,complaint_id=request.vars.complaint_id,response=request.vars.response)
        return dict(Status=1)
    # Todo: add things for hostel and insti level parts
    return dict(Status = 0)