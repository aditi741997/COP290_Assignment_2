# admin_complaint.py

def index():
	if len(request.args)<1:
		raise HTTP(404)
	return request.args[0]

def get_all_complaints():
	# scan through all 3 tables.
	if auth.is_logged_in():
		ind_comp = db(db.indiv_complaints.admin_id == auth.user.username).select()
		host_comp = db(db.hostel_complaints.admin_id == auth.user.username).select()
		inst_comp = db(db.insti_complaints.admin_id == auth.user.username).select()
		return dict(Success=True,Individual=ind_comp,Hostel=host_comp,Institute=inst_comp)
	return dict(Success=False,Complaints=[])

	
def get_complainant():
	comp_id = request.vars.complaint_id
	if (auth.is_logged_in()):
		# must check if the user is an admin.
		is_Admin = db(db.admin_info.username == auth.user.username).select()
		if len(is_Admin) > 0:
			if comp_id[:2] == "i_":
				indcom = db(db.indiv_complaints.complaint_id==compid).select()
				if len(indcom) > 0:
					user_comp = indcom[0]["username"]
			elif comp_id[:2] == "h_":
				hoscom = db(db.hostel_complaints.complaint_id==compid).select()
				if len(hoscom) > 0:
					user_comp = hoscom[0]["username"]
			elif comp_id[:2] == "in":
				inscom = db(db.insti_complaints.complaint_id==compid).select()
				if len(inscom) > 0:
					user_comp = inscom[0]["username"]
			# now get user details.
			user_details = db(db.users.username == user_comp).select()
			return dict(user_detail = user_details[0])
		return dict(user_detail = [])
	return dict(user_detail = [])


def mark_resolved():
	if (auth.is_logged_in() and complaint_id in request.vars):
		comp_id = request.vars.complaint_id
		is_Admin = db(db.admin_info.username == auth.user.username).select()
		if len(is_Admin) > 0:
			# edit the entry of the complaint in table
			if comp_id[:2] == "i_":
				db(db.indiv_complaints.complaint_id == comp_id).update(mark_for_resolution = int(request.vars.boolean_resolve))
			elif comp_id[:2] == "h_":
				db(db.hostel_complaints.complaint_id == comp_id).update(mark_for_resolution = int(request.vars.boolean_resolve))
			elif comp_id[:2] == "in":
				db(db.insti_complaints.complaint_id == comp_id).update(mark_for_resolution = int(request.vars.boolean_resolve))
			# notif to all those mapped to this complaint
			mapped_users = db(db.complaint_user_mapping.complaint_id == comp_id).select()
			for elem in mapped_users:
				# add a notif.
				if not (elem["user_id"] == auth.user.username):
					db.notifications.insert(complaint_id=comp_id,src_user_id=auth.user.username,dest_user_id=elem["user_id"],
						description="Complaint " + comp_id + " has been marked for resolution by " + auth.user.username,time_stamp=datetime.now,seen=0)
			return dict(Success = True)
		return dict(Success = False)
	return dict(Success = False)

def add_comment():
	if ("complaint_id" in request.vars and "desc" in request.vars and auth.is_logged_in()):
		comp_id = request.vars.complaint_id
		comm_desc = request.vars.desc
		# add complaint to table
		db.comments_complaint.insert(complaint_id = comp_id,user_id = auth.user.username,description = comm_desc, time_stamp = datetime.now, anonymous = 0)
		# notifs to all those mapped.
		mapped_users = db(db.complaint_user_mapping.complaint_id == comp_id).select()
		for elems in mapped_users:
			# add a notif.
			if not (elems["user_id"] == auth.user.username):
				db.notifications.insert(complaint_id = comp_id,src_user_id=auth.user.username,dest_user_id=elems["user_id"],description = "Admin " + auth.user.username + " added a comment on complaint " + comp_id,time_stamp = datetime.now,seen = 0)
		return dict(Success = True)
	return dict(Success = False)

def take_to_higher():
	if ("complaint_id" in request.vars and auth.is_logged_in()):
		# For indiv complaints only
		compid = request.vars.complaint_id
		compdetails=[]
		if (compid[:2]=="i_"):
			compdetails = db(db.indiv_complaints.complaint_id==compid).select()
		elif (compid[:2]=="h_"):
			compdetails = db(db.hostel_complaints.complaint_id==compid).select()
		elif (comp_id[:2]=="in"):
			compdetails = db(db.indiv_complaints.complaint_id==compid).select()		
		else:
			return dict(Success=False)
		if compdetails==[]:
			return dict(Success=False,description="Invalid complaint id")
		else:
			compdetails=compdetails[0]
			if (compid[:2]=="i_"):
				newcompid = GetNewCompId(0)
				comptype = compdetails["complaint_type"]
				hostelid = auth.user.hostel
				people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
				people1= []
				if len(people)==0:
					people1 = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id<0)).select(orderby=~db.admin_info.admin_level)
				if len(people1)==0:
					return dict(Success=False,description="No upper level admin found")
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
					return dict(Success=False,description="No upper level admin found") 
				db.indiv_complaints.insert(
					complaint_id=NewCompId,
					username=usname,
					complaint_type=comptype,
					complaint_content=content,		
					admin_id=newpeopleid,
					prev_id=compdetails["complain_id"])
				db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname)
				db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=compdetails["admin_id"])
				db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=newpeopleid)

				db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=newpeopleid,description="New complaint!")
				db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=compdetails["admin_id"],description="Complaint Taken to higher authority by previous admin")
				return dict(Sucess=True)
			elif (comp_id[:2]=="h_"):
				newcompid = GetNewCompId(1)
				comptype = compdetails["complaint_type"]
				hostelid = auth.user.hostel
				people = db((db.admin_info.complaint_area==comptype) & (db.admin_info.hostel_id==hostelid)).select(orderby=~db.admin_info.admin_level)
				people1= []
				if len(people)==0:
					return dict(Success=False,description="No upper level admin found")
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
					return dict(Success=False,description="No upper level admin found") 
				db.indiv_complaints.insert(
					complaint_id=NewCompId,
					username=usname,
					complaint_type=comptype,
					complaint_content=content,		
					admin_id=newpeopleid,
					prev_id=compdetails["complain_id"])
				db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=usname)
				db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=compdetails["admin_id"])
				db.complaint_user_mapping.insert(complaint_id=NewCompId,user_id=newpeopleid)

				db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=newpeopleid,description="New complaint!")
				db.notifications.insert(complaint_id=NewCompId,src_user_id=usname,dest_user_id=compdetails["admin_id"],description="Complaint Taken to higher authority by previous admin")
				return dict(Sucess=True)
			return dict(Success=False,description="Complaint not resolved yet")
