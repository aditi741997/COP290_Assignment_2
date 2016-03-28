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

# def take_to_higher():