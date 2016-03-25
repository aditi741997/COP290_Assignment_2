def index():
    if len(request.args)<1:
        raise HTTP(404)
    return request.args[0]

def GetHostel(s):
    f = db(db.users.username==s).select()[0]["hostel"]
    return f 

def GetNewCompId(x):
    # x=0 for indiv , 1 for hostel and 2 for insti
    table=None
    if x==0:
        table=db(db.indiv_complaints).select(orderby=~db.indiv_complaints.complaint_id)
    elif x==1:
        table=db(db.hostel_complaints).select(orderby=~db.hostel_complaints.complaint_id)
    else:
        table=db(db.insti_complaints).select(orderby=~db.insti_complaints.complaint_id)
    num=1
    if len(table)>0:
        cons = table[0]["complaint_id"]
        num = 1 + int(cons.split('_')[1])
    num = str(num)
    arr=["i_","h_","in_"]
    num = arr[x]+num
    return num


def add_complaint():
    if ("complaint_type" in request.vars and auth.is_logged_in() and "content" in request.vars ):
        # TODO: extend to insti and hostel
        comptype = int(request.vars.complaint_type)
        content = request.vars.content
        anon = request.vars.anonymous
        usname = auth.user.username
        hostelid = GetHostel(auth.user.username)
        # and (db.admin_info.hostel_id<0 or db.admin_info.hostel_id==GetHostel(auth.user.username()))
        people = db(db.admin_info.complaint_area==comptype and (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
        if len(people)==0:
            people = db(db.admin_info.complaint_area==comptype and (db.admin_info.hostel_id<0)).select(orderby=~db.admin_info.admin_level)
        if len(people)==0:
            return dict(Success=False)
        people=people[0]
        peopleid = people["username"]
        NewCompId=GetNewCompId(0)
        db.indiv_complaints.insert(
            complaint_id=NewCompId,
            username=usname,
            complaint_type=comptype,
            complaint_content=content,
            admin_id=peopleid)
        db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname)
        db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=peopleid)
        db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=peopleid,description="New complaint!")
        # pref = db(db.admin_info.complaint_area==comptype).select()
        # required = people[0]
        return dict(content=content,People=people, hostelid = hostelid,newid = NewCompId)            
    else:
        return dict(Success=false)

def edit_complaint():
    # if ("complaint_id" in request.vars and auth.is_logged_in()):
    # TODO: Work out later
    return dict(Success=false)


def get_complaint_details():
    if ("complaint_id" in request.vars and auth.is_logged_in()):
        compid = request.vars.complaint_id
        pref=None
        if compid[:2]=="i_":
            pref=db(db.indiv_complaints.complaint_id==compid).select()
            # Individual complaint
        elif (compid[:2]=="in"):
            pref=db(db.insti_complaints.complaint_id==compid).select()
            # Insti complaint
        elif (compid[:2]=="h_"):
            pref=db(db.hostel_complaints.complaint_id==compid).select()
            # Hostel complaint
        else:
            return dict(Details=[])
        if pref==[]:
            pref=[[]]
        return dict(Details=pref[0])
    return dict(Details=[])