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
    if (auth.is_logged_in() and "category_id" in request.vars):
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
    # TODO: Insertions in notifs table.
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

def GetAdminLevel(usname,comp_area):
    return db(db.admin_info.username==usname and db.admin_info.complaint_area==comp_area).select()[0]["admin_level"]

def high_auth():
    if ("complaint_id" in request.vars and auth.is_logged_in()):
        # For indiv complaints only
        compid = request.vars.complaint_id
        compdetails = db(db.indiv_complaints.complaint_id==compid).select()
        if compdetails==[]:
            return dict(success=False)
        else:
            compdetails=compdetails[0]
            if compdetails["username"]==auth.user.username:
                # Student wants to take it to higher
                if compdetails["resolved"]==0 and compdetails["mark_for_resolution"]==1:
                    # Student can take it above
                    newcompid = GetNewCompId(0)
                    comptype = compdetails["complaint_type"]
                    hostelid = auth.user.hostel
                    people = db(db.admin_info.complaint_area==comptype and (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
                    people1= []
                    if len(people)==0:
                        people1 = db(db.admin_info.complaint_area==comptype and (db.admin_info.hostel_id<0)).select(orderby=~db.admin_info.admin_level)
                    if len(people1)==0:
                        return dict(Success=False)
                    peoplenew = people+people1
                    bestsofar=-1             
                    newpeopleid = None
                    prevlevel=GetAdminLevel(compdetails["admin_id"],comptype)       
                    for elem  in peoplenew:
                        if elem["admin_level"]<prevlevel:
                            if bestsofar<=elem["admin_level"]:
                                bestsofar=elem["admin_level"]
                                newpeopleid=elem["username"]
                    if newpeopleid==None:
                        return dict(Success=False) 
                    db.indiv_complaints.insert(
                        complaint_id=NewCompId,
                        username=usname,
                        complaint_type=comptype,
                        complaint_content=content,
                        admin_id=newpeopleid,
                        prev_id=compdetails["complain_id"])
                    db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname)
                    db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=newpeopleid)
                    db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=newpeopleid,description="New complaint!")
                    return dict(Sucess=True)
            return dict(Success=False)






