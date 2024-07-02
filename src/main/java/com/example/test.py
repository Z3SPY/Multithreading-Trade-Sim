from py4j.java_gateway import JavaGateway, CallbackServerParameters
from threading import Condition

class PythonListener(object):
    def __init__(self, gateway):
        self.gateway = gateway
        self.condition = Condition()
        self.event_triggered = False

    def notify(self, obj):
        print("Notified by Java")
        print(obj)
        self.gateway.jvm.System.out.println("Hello from python!")
        with self.condition:
            self.event_triggered = True
            self.condition.notify()
        return "A Return Value"

    def wait_for_event(self):
        with self.condition:
            while not self.event_triggered:
                self.condition.wait()
            # Reset event for future use
            self.event_triggered = False
            print("Event received from Java!")

    class Java:
        implements = ["com.example.ProfileInterface"]

if __name__ == "__main__":
    gateway = JavaGateway(callback_server_parameters=CallbackServerParameters())
    listener = PythonListener(gateway)
    gateway.entry_point.registerListener(listener)
    print("Waiting for event from Java...")
    listener.wait_for_event()
    print("Continuing with Python code after event.")
    #gateway.shutdown()
