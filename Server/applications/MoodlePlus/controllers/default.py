# -*- coding: utf-8 -*-
# this file is released under public domain and you can use without limitations

#########################################################################
## This is a sample controller
## - index is the default action of any application
## - user is required for authentication and authorization
## - download is for downloading files uploaded in the db (does streaming)
#########################################################################

def index():
    """
    example action using the internationalization operator T and flash
    rendered by views/default/index.html or views/generic.html

    if you need a simple wiki simply replace the two lines below with:
    return auth.wiki()
    """
    response.flash = T("Welcome to Complaint Management System")
    return dict(noti_count=4)

def notifications():
    # return dict(notifcations=auth.user.username)
    noti = db(db.notifications.dest_user_id==auth.user.username).select(orderby=~db.notifications.time_stamp)
    db(db.notifications.dest_user_id==auth.user.username).update(seen=1)
    return dict(notifications=noti)

def get_hostel():
    if ("hostel_id" in request.vars):
        category_id = int(request.vars.hostel_id)
        pref=None
        if category_id>=0:
            pref =  db(db.hostel_mapping.hostel_id==category_id).select()
        else:
            pref = db(db.hostel_mapping.hostel_id>=0).select()
        return dict(Hostel=pref)
    else:
        return dict(Hostel = False)

def logged_in():
    return dict(success=auth.is_logged_in(), user=auth.user)

def logout():
    if (auth.user):
        return dict(success=True, loggedout=auth.logout())
    else:
        return dict(success="did not work")

@auth.requires_login()
def settings():
    return dict(success= True)

@auth.requires_login()
def change_pwd():
    return dict(success=True)

@auth.requires_login()
def newcomplaint():
    return dict(success=True)

@auth.requires_login()
def AllComplaints():
    tab="indiv"
    # if len(request_args)<1:
    try:
        tab=str(request.args[0])
    except:
        tab="indiv"
    return dict(success=True,tab=tab)

@auth.requires_login()
def addusers():
    return dict(success=True)

@auth.requires_login()
def complaint():
    complaint =None
    comptype=-1
    admin=0
    dummy="valid"
    comments=[]
    admindetails=None
    try:
        tab=str(request.args[0])
        if tab[:2]=="i_":
            comps = db(db.indiv_complaints.complaint_id==tab).select()
            if len(comps):
                complaint=comps[0]
                comptype=0
        elif tab[:2]=="h_":
            comps = db(db.hostel_complaints.complaint_id==tab).select()
            if len(comps):
                complaint=comps[0]
                comptype=1
        elif tab[:2]=="in":
            comps = db(db.insti_complaints.complaint_id==tab).select()
            if len(comps):
                complaint=comps[0]
                comptype=2
        if complaint:
            admin=(auth.user.username==complaint["admin_id"])
            admindetails = db(db.users.username==complaint["admin_id"]).select()[0]
        comments=db(db.comments_complaint.complaint_id==tab).select()
    except:
        y=5
        dummy="invalid"
    return dict(complaint=complaint,comptype=comptype,admindetails=admindetails,admin=admin,comments=comments,dummy=dummy)

def managecomplaints():
    return dict(success=True)

def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/manage_users (requires membership in
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    """
    return dict(form=auth())


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)


def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()


@request.restful()
def api():
    response.view = 'generic.'+request.extension
    def GET(*args,**vars):
        patterns = 'auto'
        parser = db.parse_as_rest(patterns,args,vars)
        if parser.status == 200:
            return dict(content=parser.response)
        else:
            raise HTTP(parser.status,parser.error)
    def POST(table_name,**vars):
        return db[table_name].validate_and_insert(**vars)
    def PUT(table_name,record_id,**vars):
        return db(db[table_name]._id==record_id).update(**vars)
    def DELETE(table_name,record_id):
        return db(db[table_name]._id==record_id).delete()
    return dict(GET=GET, POST=POST, PUT=PUT, DELETE=DELETE)

def login():
    userid = request.vars.userid
    password = request.vars.password
    user = auth.login_bare(userid,password)
    return dict(success=False if not user else True, Unique_Id=user["username"] if user else "",userid =userid,passwd =password)

def change_pass():
    # oldpassword = request.vars.oldpwd
    newpassword = request.vars.newpwd
    table_user=auth.settings.table_user
    passfield = auth.settings.password_field
    s=db(table_user.id== auth.user_id)
    d={passfield: newpassword}
    temp = s.validate_and_update(**d)
    return dict(success= temp)

def clear_db():
    for table in db.tables():
        try:
            db(db[table].id>0).delete()
            print "Cleared",table
        except Exception, e:
            print "Couldn't clear",table

def populate_db():
    ## Populate DB Script

    ## clear database
    for table in db.tables():
        try:
            db(db[table].id>0).delete()
            print "Cleared",table
        except Exception, e:
            print "Couldn't clear",table

    ## create 4 students
    db.users.insert(
        name="Aditi",
        user_type=0,
        username="cs1140205",
        contact_number="dhinchak",
        hostel=1,
        password="aditi",
    )
    
    db.users.insert(
        name="Ayush",
        user_type=0,
        username="cs1140091",
        contact_number="blah blah",
        hostel=2,
        password="ayush",
    )
    


    db.users.insert(
        name="Nikhil",
        user_type=0,
        username="cs5140462",
        contact_number="blah blah blah",
        hostel=3,
        password="nikhil",
    )

    db.users.insert(
        name ="elec1",
        user_type=1,
        username="a1234",
        contact_number="blah blah",
        hostel=-1,
        password="elec1"
    )

    db.admin_info.insert(
        username="a1234",
        complaint_area=0,
        admin_level=2,
        description='Electriction for all hostels',
        hostel_id=1,
    )
    
    db.admin_info.insert(
        username="a12345",
        complaint_area=0,
        admin_level=1,
        description='Senior Electriction for all hostels',
        hostel_id=1,
    )

    db.complaint_category.insert(
        category_id=0,
        category_description="Electric Stuff"
    )
    
    db.complaint_category.insert(
        category_id=1,
        category_description="Water Stuff"
    )
    
    db.complaint_category.insert(
        category_id=2,
        category_description="Maintainence"
    )

    db.complaint_category.insert(
        category_id=3,
        category_description="Generic"
    )

    db.complaint_category.insert(
        category_id=4,
        category_description="Sports"
    )

    db.hostel_mapping.insert(
        hostel_id=0 ,
        hostel_name="Himadri"
    )
    
    db.hostel_mapping.insert(
        hostel_id=1 ,
        hostel_name="Udaigiri"
    )

    db.hostel_mapping.insert(
        hostel_id=2 ,
        hostel_name="Girnar"
    )
    
    db.notifications.insert(
        complaint_id="i_123",
        src_user_id="a1234",
        dest_user_id="cs1140205",
        description="time pass",  
    )

    db.notifications.insert(
        complaint_id="i_1",
        src_user_id="cs1130231",
        dest_user_id="cs5140462",
        description="Test notification 1"
    )
    
    db.notifications.insert(
        complaint_id="i_2",
        src_user_id="cs5140462",
        dest_user_id="cs5140462",
        description="Test notification 2 for complaint 2"
    )
    
    db.notifications.insert(
        complaint_id="i_3",
        src_user_id="cs1140205",
        dest_user_id="cs5140462",
        description="Test notification 3 for complaint 3"
    )

    db.indiv_complaints.insert(
        complaint_id="i_1",
        username="cs5140462",
        complaint_type=1,
        complaint_content="Complaint number 1",
        extra_info="Extra info for comp 1",
        admin_id="a1234"
    )

    db.indiv_complaints.insert(
        complaint_id="i_2",
        username="cs5140462",
        complaint_type=1,
        complaint_content="Complaint number 2",
        extra_info="Extra info for comp 2",
        admin_id="a1234"
    )
    db.indiv_complaints.insert(
        complaint_id="i_3",
        username="cs5140462",
        complaint_type=1,
        complaint_content="Complaint number 3",
        extra_info="Extra info for comp 3",
        admin_id="a1234"
    )

    db.complaint_user_mapping.insert(
        complaint_id="i_1",
        user_id="cs5140462"
    )
    db.complaint_user_mapping.insert(
        complaint_id="i_2",
        user_id="cs5140462"
    )
    db.complaint_user_mapping.insert(
        complaint_id="i_3",
        user_id="cs5140462"
    )

    db.hostel_complaints.insert(
        complaint_id="h_1",
        username="cs5140462",
        complaint_content="Hostel complaint 1",
        extra_info="Details of complaint",
        complaint_type=2,
        admin_id="a12345",
        hostel='2'
    )

    db.complaint_user_mapping.insert(
        complaint_id="h_1",
        user_id="cs5140462"
    )

    db.complaint_user_mapping.insert(
        complaint_id="h_1",
        user_id="a12345"
    )
    
    db.insti_complaints.insert(
        complaint_id="in_1",
        username="cs5140462",
        complaint_content="Institute complaint 1",
        extra_info="Details of complaint",
        complaint_type=2,
        admin_id="a12345",
        anonymous=1,
    )

    db.complaint_user_mapping.insert(
        complaint_id="in_1",
        user_id="cs5140462"
    )

    db.complaint_user_mapping.insert(
        complaint_id="in_1",
        user_id="a12345"
    )

    # ## create 7 courses
    # db.courses.insert(
    #     name="Design Practices in Computer Science",
    #     code="cop290",
    #     description="Design Practices in Computer Science.",
    #     credits=3,
    #     l_t_p="0-0-6"
    # )

    # db.courses.insert(
    #     name="Wireless Networks",
    #     code="csl838",
    #     description="PHY and MAC layer concepts in wireless networking",
    #     credits=3,
    #     l_t_p="2-0-2"
    # )

    # db.courses.insert(
    #     name="Software Engineering",
    #     code="col740",
    #     description="Introduction to the concepts of Software Design and Engineering.",
    #     credits=4,
    #     l_t_p="3-0-2"
    # )

    # db.courses.insert(
    #     name="Cloud Computing and Virtualisation",
    #     code="csl732",
    #     description="Introduction to Cloud Computing and Virtualisation.",
    #     credits=4,
    #     l_t_p="3-0-2"
    # )

    # db.courses.insert(
    #     name="Parallel Programming",
    #     code="col380",
    #     description="Introduction to concurrent systems and programming style.",
    #     credits=4,
    #     l_t_p="3-0-2"
    # )

    # db.courses.insert(
    #     name="Computer Graphics",
    #     code="csl781",
    #     description="Computer Graphics.",
    #     credits=4,
    #     l_t_p="3-0-2"
    # )


    # db.courses.insert(
    #     name="Advanced Computer Graphics",
    #     code="csl859",
    #     description="Graduate course on Advanced Computer Graphics",
    #     credits=4,
    #     l_t_p="3-0-2"
    # )




    # ## create 7 registered courses
    # db.registered_courses.insert(    
    #     course_id=1,
    #     professor=5,
    #     year_=2016,
    #     semester=2,
    #     starting_date=datetime(2016,1,1),
    #     ending_date=datetime(2016,5,10),
    # )

    # db.registered_courses.insert(
    #     course_id=2,
    #     professor=5,
    #     year_=2016,
    #     semester=2,
    #     starting_date=datetime(2016,1,1),
    #     ending_date=datetime(2016,5,10),
    # )

    # db.registered_courses.insert(
    #     course_id=3,
    #     professor=6,
    #     year_=2016,
    #     semester=2,
    #     starting_date=datetime(2016,1,1),
    #     ending_date=datetime(2016,5,10),
    # )

    # db.registered_courses.insert(
    #     course_id=4,
    #     professor=6,
    #     year_=2016,
    #     semester=2,
    #     starting_date=datetime(2016,1,1),
    #     ending_date=datetime(2016,5,10),
    # )

    # db.registered_courses.insert(
    #     course_id=5,
    #     professor=7,
    #     year_=2016,
    #     semester=2,
    #     starting_date=datetime(2016,1,1),
    #     ending_date=datetime(2016,5,10),
    # )

    # db.registered_courses.insert(
    #     course_id=6,
    #     professor=7,
    #     year_=2016,
    #     semester=2,
    #     starting_date=datetime(2016,1,1),
    #     ending_date=datetime(2016,5,10),
    # )

    # db.registered_courses.insert(
    #     course_id=7,
    #     professor=7,
    #     year_=2016,
    #     semester=1,
    #     starting_date=datetime(2014,7,1),
    #     ending_date=datetime(2014,12,10),
    # )

    # ## register 3 students for 5 courses each out of 7 registered courses
    # db.student_registrations.insert(student_id=1,registered_course_id=3)
    # db.student_registrations.insert(student_id=1,registered_course_id=2)
    # db.student_registrations.insert(student_id=1,registered_course_id=1)
    # db.student_registrations.insert(student_id=1,registered_course_id=4)
    # db.student_registrations.insert(student_id=1,registered_course_id=5)
    # db.student_registrations.insert(student_id=2,registered_course_id=3)
    # db.student_registrations.insert(student_id=2,registered_course_id=4)
    # db.student_registrations.insert(student_id=2,registered_course_id=6)
    # db.student_registrations.insert(student_id=2,registered_course_id=7)
    # db.student_registrations.insert(student_id=2,registered_course_id=1)
    # db.student_registrations.insert(student_id=3,registered_course_id=3)
    # db.student_registrations.insert(student_id=3,registered_course_id=1)
    # db.student_registrations.insert(student_id=3,registered_course_id=5)
    # db.student_registrations.insert(student_id=3,registered_course_id=6)
    # db.student_registrations.insert(student_id=3,registered_course_id=2)
    # db.student_registrations.insert(student_id=4,registered_course_id=3)
    # db.student_registrations.insert(student_id=4,registered_course_id=4)
    # db.student_registrations.insert(student_id=4,registered_course_id=5)
    # db.student_registrations.insert(student_id=4,registered_course_id=7)
    # db.student_registrations.insert(student_id=4,registered_course_id=1)

    # ## create 3 assignments in Design Practices course
    # db.events.insert(
    #     registered_course_id=1,
    #     type_=0,
    #     name="Project Submission 1: Draft Requirement Document",
    #     description="<p><br></p><p>Organise 2 hr meeting of the team to</p><p>-Choose one of the Projects discussed in the class</p><p>-Discuss the specification of the selected project. Identify the aspects to be explored by team members&nbsp;</p><p>-Document the discussion and the initial specs of the project</p><p><br></p><p>Organise 2nd 2 hr meeting &nbsp;to</p><p>-Share the homework done by each team member</p><p>-Discuss and finalise the specs of the projects</p><p>-Prepare 1 or 2 page document on the draft project specification&nbsp;</p><p><br></p><p>Submit the draft Project Requirement Document by Wednesday mid night.</p><p>Add title of the project in the group excel sheet</p>",
    #     created_at=datetime.now(),
    #     deadline=datetime.now()-timedelta(days=-7),
    #     late_days_allowed=0
    # )

    # db.events.insert(
    #     registered_course_id=1,
    #     type_=0,
    #     name="Project Submission 2: Requirement Document in IEEE template format",
    #     description="<p>Submission Deadline 20 Feb Midnight.</p><p id='yui_3_17_2_3_1431040674495_308'>Recommended Process</p><p>-Meeting1 &nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Compete listing &nbsp;of User requirements</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Create System Architecture</p><p id='yui_3_17_2_3_1431040674495_309'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Identify Use cases, Users, draw Use Case Diagram<br></p><p>-Meeting2</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Translate user requirement into system requirements</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Discuss and document Use cases including relevant Models</p><p>-Meeting3&nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; Discuss each section of the IEEE template&nbsp;</p><p>&nbsp; &nbsp; &nbsp; &nbsp; Create Document as per IEEE template</p><p><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>&nbsp;</p><p><br></p>",
    #     created_at=datetime.now(),
    #     deadline=datetime.now()-timedelta(days=-7),
    #     late_days_allowed=2
    # )

    # db.events.insert(
    #     registered_course_id=1,
    #     type_=0,
    #     name="Project Submission 3 : Detailed Design Document",
    #     description="<p>Based on the Requirement Document, a detailed design document</p><p>will be created by each group.</p><p>It should have the following components</p><p>-Project Overview</p><p>-Architectural design with well identified Modules</p><p>-Modules specification &amp; its APIs</p><p>-Database Design</p><p>-User Interface Design</p><p>-Module internal data structures and processing if needed.</p><p>-Aprorpriate Diagrams as necessary</p><p>-Any other information as necessary</p><p>Design document should be complete from all aspects ( i.e Requirement &amp; design document should be adequate for any other programming team to develop the system without may further input)</p><p>You may use any apropriate format for this design document</p><p>Submission date 29th March.</p><p><br></p><p><br></p>",
    #     created_at=datetime.now(),
    #     deadline=datetime.now()-timedelta(days=-7),
    #     late_days_allowed=3
    # )

    # ## Grades
    # db.grades.insert(user_id=1, registered_course_id=1, name="Assignment 1", score=15, out_of=15, weightage=10)
    # db.grades.insert(user_id=1, registered_course_id=1, name="Assignment 2", score=10, out_of=20, weightage=15)
    # db.grades.insert(user_id=1, registered_course_id=1, name="Minor 1", score=25, out_of=30, weightage=25)

    # db.grades.insert(user_id=2, registered_course_id=1, name="Assignment 1", score=15, out_of=15, weightage=10)
    # db.grades.insert(user_id=2, registered_course_id=1, name="Assignment 2", score=18, out_of=20, weightage=15)
    # db.grades.insert(user_id=2, registered_course_id=1, name="Minor 1", score=20, out_of=30, weightage=25)

    # db.grades.insert(user_id=3, registered_course_id=1, name="Assignment 1", score=15, out_of=15, weightage=10)
    # db.grades.insert(user_id=3, registered_course_id=1, name="Assignment 2", score=14, out_of=20, weightage=15)
    # db.grades.insert(user_id=3, registered_course_id=1, name="Minor 1", score=23, out_of=30, weightage=25)

    # db.grades.insert(user_id=4, registered_course_id=1, name="Assignment 1", score=15, out_of=15, weightage=10)
    # db.grades.insert(user_id=4, registered_course_id=1, name="Assignment 2", score=20, out_of=20, weightage=15)
    # db.grades.insert(user_id=4, registered_course_id=1, name="Minor 1", score=15, out_of=30, weightage=25)

    # ## create 4 threads in Different courses


    # ## Create Static Variables
    # db.static_vars.insert(
    #     name="current_year",
    #     int_value=2016,
    #     string_value="2016"
    # )

    # db.static_vars.insert(
    #     name="current_sem",
    #     int_value=2,
    #     string_value="2"
    # )
def api():
    return """
Moodle Plus API (ver 1.0)
-------------------------

Url: /default/login.json
Input params:
    userid: (string)
    password: (string)
Output params:
    success: (boolean) True if login success and False otherwise
    user: (json) User details if login is successful otherwise False


Url: /default/logout.json
Input params:
Output params:
    success: (boolean) True if logout successful and False otherwise


Url: /courses/list.json
Input params:
Output params:
    current_year: (int)
    current_sem: (int) 0 for summer, 1 break, 2 winter
    courses: (List) list of courses
    user: (dictionary) user details

Url: /threads/new.json
Input params:
    title: (string) can't be empty
    description: (string) can't be empty
    course_code: (string) must be a registered courses
Output params:
    success: (bool) True or False depending on whether thread was posted
    thread_id: (bool) id of new thread created

    """
