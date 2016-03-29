# -*- coding: utf-8 -*-

#########################################################################
## This scaffolding model makes your app work on Google App Engine too
## File is released under public domain and you can use without limitations
#########################################################################

## if SSL/HTTPS is properly configured and you want all HTTP requests to
## be redirected to HTTPS, uncomment the line below:
# request.requires_https()

## app configuration made easy. Look inside private/appconfig.ini
from gluon.contrib.appconfig import AppConfig
## once in production, remove reload=True to gain full speed
myconf = AppConfig(reload=True)



if not request.env.web2py_runtime_gae:
    ## if NOT running on Google App Engine use SQLite or other DB
    db = DAL(myconf.take('db.uri'), pool_size=myconf.take('db.pool_size', cast=int), check_reserved=['all'], migrate=True)
else:
    ## connect to Google BigTable (optional 'google:datastore://namespace')
    db = DAL('google:datastore+ndb', migrate=True)
    ## store sessions and tickets there
    session.connect(request, response, db=db)
    ## or store session in Memcache, Redis, etc.
    ## from gluon.contrib.memdb import MEMDB
    ## from google.appengine.api.memcache import Client
    ## session.connect(request, response, db = MEMDB(Client()))

## by default give a view/generic.extension to all actions from localhost
## none otherwise. a pattern can be 'controller/function.extension'
response.generic_patterns = ['*'] # if request.is_local else []
## choose a style for forms
response.formstyle = myconf.take('forms.formstyle')  # or 'bootstrap3_stacked' or 'bootstrap2' or other
response.form_label_separator = myconf.take('forms.separator')


## (optional) optimize handling of static files
# response.optimize_css = 'concat,minify,inline'
# response.optimize_js = 'concat,minify,inline'
## (optional) static assets folder versioning
# response.static_version = '0.0.0'
#########################################################################
## Here is sample code if you need for
## - email capabilities
## - authentication (registration, login, logout, ... )
## - authorization (role based authorization)
## - services (xml, csv, json, xmlrpc, jsonrpc, amf, rss)
## - old style crud actions
## (more options discussed in gluon/tools.py)
#########################################################################

from gluon.tools import Auth, Service, PluginManager

auth = Auth(db)
service = Service()
plugins = PluginManager()

## create all tables needed by auth if not custom tables
auth.define_tables(username=False, signature=False)

## configure email
mail = auth.settings.mailer
mail.settings.server = 'logging' if request.is_local else myconf.take('smtp.sender')
mail.settings.sender = myconf.take('smtp.sender')
mail.settings.login = myconf.take('smtp.login')

## configure auth policy
auth.settings.registration_requires_verification = False
auth.settings.registration_requires_approval = False
auth.settings.reset_password_requires_verification = True

#########################################################################
## Define your tables below (or better in another model file) for example
##
## >>> db.define_table('mytable',Field('myfield','string'))
##
## Fields can be 'string','text','password','integer','double','boolean'
##       'date','time','datetime','blob','upload', 'reference TABLENAME'
## There is an implicit 'id integer autoincrement' field
## Consult manual for more options, validators, etc.
##
## More API examples for controllers:
##
## >>> db.mytable.insert(myfield='value')
## >>> rows=db(db.mytable.myfield=='value').select(db.mytable.ALL)
## >>> for row in rows: print row.id, row.myfield
#########################################################################

## after defining tables, uncomment below to enable auditing
# auth.enable_record_versioning(db)

from datetime import datetime,timedelta

db.define_table(
    'users',
    Field('name', length=128, default=''),
    Field('user_type', 'integer'),
    Field('username', length=100, unique=True), #cs5110272
    Field('contact_number',length=128,default=''),
    Field('hostel','integer'),
    Field('other_details',length=1024,default=''),
    Field('hostel_pref',length=100,default='1'*10),
    Field('insti_pref',length=100,default='1'*10),
    Field('extra_pref',length=100,default='1'*10),
    Field('password', 'password', length=512, readable=False, label='Password'),
    Field('registration_key', length=512, writable=False, readable=False, default=''),
    Field('reset_password_key', length=512, writable=False, readable=False, default=''),
    Field('registration_id', length=512, writable=False, readable=False, default=''),
 )


custom_auth_table = db['users']

auth.settings.table_user = custom_auth_table
auth.settings.table_user_name = 'users'    #Very important to mention
auth.settings.table_group_name = 'user_group'
auth.settings.password_field = 'password'
auth.settings.table_membership_name = 'user_membership'
auth.settings.table_permission_name = 'user_permission'
auth.settings.table_event_name = 'user_event'
auth.settings.login_userfield = 'username'        #the loginfield will be username
auth.define_tables(username=False)    #Creating the table

db.define_table(
    'admin_info',
    Field('username','string',unique=False),
    Field('complaint_area','integer',unique=False),
    Field('admin_level','integer',unique=False),
    Field('description','string',unique=False),
    Field('hostel_id','integer',unique=False),
)

db.define_table(
    'complaint_response',
    Field('username','string'),
    Field('complaint_id','string'),
    Field('response','integer'),
)

db.define_table(
    'hostel_mapping',
    Field('hostel_id','integer',unique=True),
    Field('hostel_name','string'),
)

db.define_table(
    'complaint_category',
    Field('category_id','integer',unique=True),
    Field('category_description','string'),
)

db.define_table(
    'comments_complaint',
    Field('complaint_id','string'),
    Field('user_id','string'),
    Field('description','string'),
    Field('time_stamp','datetime',default=datetime.now),
    Field('anonymous','integer',default=0),
)

db.define_table(
    'complaint_user_mapping',
    Field('complaint_id','string'),
    Field('user_id','string'),
)

db.define_table(
    'notifications',
    Field('complaint_id','string'),
    Field('src_user_id','string'),
    Field('dest_user_id','string'),
    Field('description','string'),
    Field('time_stamp','datetime',default=datetime.now),
    Field('seen','integer',default=0),
)


db.define_table(
    'user_satisfaction_response',
    Field('username','string'),
    Field('complaint_id','string'),
    Field('response','integer'),
)

db.define_table(
    'indiv_complaints',
    Field('complaint_id','string',unique=True),
    Field('username','string'),
    Field('complaint_type','integer'),
    Field('complaint_content','string'),
    Field('extra_info','string',default=""),
    Field('admin_id','string'),
    Field('time_stamp','datetime',default=datetime.now),
    Field('resolved','integer',default=0),
    Field('mark_for_resolution','integer',default=0),
    Field('comment_comp','string',default=""),
    Field('prev_id','string',default=""),
)

db.define_table(
    'hostel_complaints',
    Field('complaint_id','string',unique=True),
    Field('username','string'),
    Field('complaint_type','integer'),
    Field('extra_info','string',default=""),
    Field('complaint_content','string'),
    Field('admin_id','string'),
    Field('time_stamp','datetime',default=datetime.now),
    Field('resolved','integer',default=0),
    Field('mark_for_resolution','integer',default=0),
    Field('comment_comp','string',default=""),
    Field('prev_id','string',default=""),
    Field('hostel','integer'),
    Field('anonymous','integer',default=0),
    Field('num_up','integer',default=0),
    Field('num_down','integer',default=0),
    Field('num_neutr','integer',default=0),
    Field('num_sat','integer',default=0),
    Field('num_unsat','integer',default=0),
)

db.define_table(
    'insti_complaints',
    Field('complaint_id','string',unique=True),
    Field('username','string'),
    Field('complaint_type','integer'),
    Field('extra_info','string',default=""),
    Field('complaint_content','string'),
    Field('admin_id','string'),
    Field('time_stamp','datetime',default=datetime.now),
    Field('resolved','integer',default=0),
    Field('mark_for_resolution','integer',default=0),
    Field('comment_comp','string',default=""),
    Field('prev_id','string',default=""),
    Field('anonymous','integer',default=0),
    Field('num_up','integer',default=0),
    Field('num_down','integer',default=0),
    Field('num_neutr','integer',default=0),
    Field('num_sat','integer',default=0),
    Field('num_unsat','integer',default=0),
)

# db.define_table(
#     'user_vars',
#     Field('user_id',db.users),
#     Field('name','string'),
#     Field('int_value','integer'),
#     Field('string_value','string'),
# )

# db.define_table(
#     'courses',
#     Field('name','string'),
#     Field('code','string'),
#     Field('description','string'),
#     Field('credits','integer'),
#     Field('l_t_p','string'), # 3-0-1
# )

# db.define_table(
#     'registered_courses',
#     Field('course_id',db.courses),
#     Field('professor',db.users),
#     Field('year_','integer'),
#     Field('semester','integer'), # 0,1,2 summer, break, fall
#     Field('starting_date','datetime'),
#     Field('ending_date','datetime'),
# )

# db.define_table(
#     'student_registrations',
#     Field('student_id',db.users),
#     Field('registered_course_id',db.registered_courses),
# )

# db.define_table(
#     'ta_registrations',
#     Field('user_id', db.users),
#     Field('registered_course_id',db.registered_courses),
# )

# db.define_table(
#     'events',
#     Field('registered_course_id', db.registered_courses),
#     Field('type_','integer'), # 0 assignment, 1 announcement
#     Field('name','string'),
#     Field('description','string'),
#     Field('created_at','datetime',default=datetime.now),
#     Field('deadline','datetime'),
#     Field('late_days_allowed','integer'),
#     Field('file_','upload'),
# )

# db.define_table(
#     'grades',
#     Field('registered_course_id',db.registered_courses),
#     Field('user_id', db.users),
#     Field('name','string'),
#     Field('score','double'),
#     Field('out_of','double'),
#     Field('weightage','double'),
# )

# db.define_table(
#     'resources',
#     Field('registered_course_id',db.registered_courses),
#     Field('user_id',db.users),
#     Field('is_public','integer'),
#     Field('name','string'),
#     Field('file_','upload'),
#     Field('created_at','datetime',default=datetime.now),
# )

# db.define_table(
#     'submissions',
#     Field('user_id',db.users),
#     Field('event_id', db.events),
#     Field('name', 'string'),
#     Field('file_', 'upload'),
#     Field('created_at','datetime',default=datetime.now)
# )

# db.define_table(
#     'threads',
#     Field('user_id', db.users),
#     Field('registered_course_id', db.registered_courses),
#     Field('created_at', 'datetime', default=datetime.now),
#     Field('updated_at', 'datetime', default=datetime.now),
#     Field('title','string'),
#     Field('description', 'string'),
# )

# db.define_table(
#     'notifications',
#     Field('user_id', db.users),
#     Field('description', 'string'),
#     Field('is_seen', 'integer', default=0),
#     Field('created_at', 'datetime', default=datetime.now),
# )

# db.define_table(
#     'comments',
#     Field('thread_id', db.threads),
#     Field('user_id', db.users),
#     Field('description', 'string'),
#     Field('created_at', 'datetime', default=datetime.now),
# )