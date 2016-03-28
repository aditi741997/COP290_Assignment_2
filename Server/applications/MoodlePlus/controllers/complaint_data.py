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
        typecomp = request.vars.type
        comptype = request.vars.complaint_type
        comptype = db(db.complaint_category.category_description==comptype).select()[0]["category_id"]
        content = request.vars.content
        anon = int(request.vars.anonymous)
        usname = auth.user.username
        extradet = ""
        try:
            extradet=request.vars.extra_info
        except:
            extradet=""
        hostelid = GetHostel(auth.user.username)
        # and (db.admin_info.hostel_id<0 or db.admin_info.hostel_id==GetHostel(auth.user.username()))
        if (typecomp=="i"):
            people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
            if len(people)==0:
                people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id<0)).select(orderby=~db.admin_info.admin_level)
            if len(people)==0:
                return dict(Success=False,comptype=comptype)
            people=people[0]
            peopleid = people["username"]
            NewCompId=GetNewCompId(0)
            db.indiv_complaints.insert(
                complaint_id=NewCompId,
                username=usname,
                complaint_type=comptype,
                complaint_content=content,
                extra_info=extradet,
                admin_id=peopleid)
            db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname)
            db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=peopleid)
            db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=peopleid,description="New complaint!")
            # pref = db(db.admin_info.complaint_area==comptype).select()
            # required = people[0]
            return dict(Success=True, content=content,People=people, hostelid = hostelid,newid = NewCompId)            
        elif (typecomp=="h"):
            people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
            if len(people)==0:
                people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id<0)).select(orderby=~db.admin_info.admin_level)
            if len(people)==0:
                return dict(Success=False)
            people=people[0]
            peopleid = people["username"]
            NewCompId=GetNewCompId(1)
            db.hostel_complaints.insert(
                complaint_id=NewCompId,
                username=usname,
                complaint_type=comptype,
                complaint_content=content,
                extra_info=extradet,
                anonymous=anon,
                hostel=hostelid,
                admin_id=peopleid)
            db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname) 
            db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=peopleid)
            db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=peopleid,description="New complaint!")
            # pref = db(db.admin_info.complaint_area==comptype).select()
            # required = people[0]
            return dict(Success=True,content=content,People=people, hostelid = hostelid,newid = NewCompId)            
        
        elif (typecomp=="in"):
            # people = db(db.admin_info.complaint_area==comptype and (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
            # if len(people)==0:
            people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id<0)).select(orderby=~db.admin_info.admin_level)
            if len(people)==0:
                return dict(Success=False)
            people=people[0]
            peopleid = people["username"]
            NewCompId=GetNewCompId(2)
            db.insti_complaints.insert(
                complaint_id=NewCompId,
                username=usname,
                complaint_type=comptype,
                complaint_content=content,
                anonymous=anon,
                extra_info=extradet,
                admin_id=peopleid)
            db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname)
            db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=peopleid)
            db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=peopleid,description="New complaint!")
            # pref = db(db.admin_info.complaint_area==comptype).select()
            # required = people[0]
            return dict(Success=True,content=content,People=people, hostelid = hostelid,newid = NewCompId)                    
    else:
        return dict(Success=False,LoggedIn=auth.is_logged_in())

def edit_complaint():
    # if ("complaint_id" in request.vars and auth.is_logged_in()):
    # TODO: Work out later
    return dict(Success=True)

def get_all():
    if (auth.is_logged_in()):
        hostelpref = map(int,list(db(db.users.user_id == auth.user.username).select()[0]["hostel_pref"]))
        instipref = map(int,list(db(db.users.user_id == auth.user.username).select()[0]["insti_pref"]))
        # TODO: show all complaints of preferences, rather than all those mapped.
        allcomp = db(db.complaint_user_mapping.user_id==auth.user.username).select()
        IndivComp = []
        HostelComp = []
        InstiComp = []
        for elem in allcomp:
            compid = elem["complaint_id"]
            if compid[:2]=="i_":
                complaint = db(db.indiv_complaints.complaint_id==compid).select()[0]
                IndivComp.append(complaint)
        #     elif compid[:2]=="h_":
        #         complaint = db(db.hostel_complaints.complaint_id==compid).select()[0]
        #         HostelComp.append(complaint)
        #     elif compid[:2]=="in":
        #         complaint = db(db.insti_complaints.complaint_id==compid).select()[0]
        #         InstiComp.append(complaint)
        hostelid = db(db.user.user_id==auth.user.username).select()[0]["hostel"]
        for i in xrange(len(hostelpref)):
            if hostelpref[i]:
                HostelComp += db((db.hostel_complaints.hostel==hostelid) & (db.hostel_complaints.complaint_type==i)).select()
            if instipref[i]:
                InstiComp += db(db.insti_complaints.complaint_type==i).select()

        return dict(Individual=IndivComp, Hostel = HostelComp, Institute = InstiComp)
    return dict(Complaints=[])

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
        category=""
        if pref==[]:
            pref=[[]]
        else:
            category=db(db.complaint_category.category_id==pref[0]["complaint_type"]).select()[0]["category_description"]
        admin_No = ""
        if pref == []:
            pref = [[]]
        else:
            admin_No = db(db.users.username == pref[0]["admin_id"]).select()[0]["contact_number"]
        return dict(Details=pref[0],category=category,timest=timeHuman(pref[0]["time_stamp"]), adminNo=admin_No)
    return dict(Details=[])