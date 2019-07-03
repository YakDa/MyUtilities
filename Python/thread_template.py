import threading


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