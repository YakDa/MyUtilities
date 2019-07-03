import threading


####################################################################################
# cmd start                   --- start the obj thread
# cmd stop                    --- stop the obj thread
# cmd -a [action]             --- change the action of obj thread
####################################################################################
def interpreter(parameters):

	global obj

	if len(parameters) == 0:
		if not obj.isAlive():
			obj = myThread(1, "my-Thread", "parameters")
			obj.start()
		else:
			print("Sir, obj thread has been started, you cannot start another one without stopping the current one\n")
		return

	while len(parameters) != 0:

		if parameters[0] == "start":
			if not obj.isAlive():
				obj = myThread(1, "my-Thread", "parameters")
				obj.start()
			else:
				print("Sir, obj thread has been started, you cannot start another one without stopping the current one\n")
			del parameters[0]
		elif parameters[0] == "stop":
			print("obj application has been quited\n")
			obj.stop()
			obj.join()
			del parameters[0]
		elif parameters[0] == "-a":
			obj.parameters = parameters[1]
			del parameters[0:2] 
		else:
			print("Sorry Sir, I don't understand your instructions\n")
			break

# thread class, inhereted from threading.Thread class
class myThread(threading.Thread):
	# class constructor of the class
	def __init__(self, threadID, name, parameters):
		# threading.Thread.__init__ is the system init functions, 
		# it could allocate the resources needed by this thread in OS level
		threading.Thread.__init__(self)
		self.threadID = threadID
		self.name = name
		self.parameters = parameters

        # define a event for thread termination
		self._stop_event = threading.Event()

	def run(self):


		while True:
			if self.stopped():
				# Do cleanup here..........
				break
			# Do something here..........


    #-------------------------------------------------------------
    # to set the stop flag
    #-------------------------------------------------------------
	def stop(self):
		self._stop_event.set()

	#-------------------------------------------------------------
	# to check the stop flag if the thread should be terminated
	#-------------------------------------------------------------
	def stopped(self):
		return self._stop_event.is_set()

# executed when import
obj = myThread(1, "my-Thread", "parameters")