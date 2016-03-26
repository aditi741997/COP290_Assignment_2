# admin_complaint.py

def index():
	if len(request.args)<1:
		raise HTTP(404)
	return request.args[0]

def get_all_complaints():

def get_complainant():

def mark_resolved():

def add_comment():

def take_to_higher():