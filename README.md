[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


![Alt text](https://bitbucket.org/devapo/camunda-jms-connector/raw/be613f14158caab39b30f458235f3eac1d3b61b1/resources/IMAGES/devapo.PNG "Devapo")

## About The Project

Camunda JMS Connector is a Cammunda Connect plugin extension that allows to enqueue and dequeue messages on Java Message Service in any project that uses Cammunda Engine with BPMN processes.
It is fully customizable and by using it in a project, a user can model a BPMN process where it is possible to enqueue any messages on a particular instance of JMS. JMS Connector also features
receiveing messages from  the desirable queue and upon receiving the payload it triggers a message in the Camunda Engine, which ultimately can start a new instance of a process or continue an 
existing one depending on how the connector and a processes are customized.

![Alt text](https://bitbucket.org/devapo/camunda-jms-connector/raw/9ea400c94a2923cb0f75fb760e278a57da64d201/resources/IMAGES/Camunda%20Flow.png "Cammunda flow")

### Built With

* []() Java
* []() Spring Boot
* []() Camunda Engine
* []() Camunda Connect extension
* []() Camunda Modeler
* []() ActiveMQ JMS

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

* []() clone Cammunda JMS Connector repository
* []() get your favourite IDE
* []() maven
* []() ActiveMQ - a local instance in Docker should do
* []() Camunda Modeler

### Installation

1. **Customize listener**  
	* To choose the queue that you want to listen get into `TransactionReceiver.java` and change the value of **destination** in `@JmsListener` annotation.
	* To choose the instance of a JMS that you would like to listen get into your project and customize necessary variables in `application.properites`.
```
// Example application.properites

//activemq credentials
spring.activemq.user=admin
spring.activemq.password=admin

//activemq url
spring.activemq.broker-url=tcp://localhost:61616?jms.redeliveryPolicy.maximumRedeliveries=1

//json keys that you would like your listener should look for

//key for an id of an instance of a process 
json.instanceid=bkey

//key for a message that you would like to send
json.payload=msg
```  

2.  **Customize publisher**
	* Select `service-task` in cammunda modeler block and customize it according to your needs. Example customization below will enqueue on `test` queue that is run on an `failover://tcp://localhost:61616` address a message that is a json - `{'bkey':'first','msg':'start'}`. Mind the fact that those values reflect on how you customized your listener.   
![Alt text](https://bitbucket.org/devapo/camunda-jms-connector/raw/d5897526a72425cad0ce1752ead0a023055bab4d/resources/IMAGES/publisher.PNG)

3.  **Build the project**    
    * run `mvn package` in respository directory  
4.  **Use output JAR's as external library**  
   In `\target` directory of listener and publisher you will find both fat jars and light jars. Use one of them regarding your project preferences. In test project those jars are included already in **pom.xml**.

## Example usage

* Start local ActiveMQ JMS. Be default it will run on `tcp://localhost:61616?jms.redeliveryPolicy.maximumRedeliveries=1` URL but remember to change it in *application.properties* of your project if you don't go for the default value.

* Open the `camunda-jms-test-project` with your favourite IDE.

* Start the project. Make sure that you included publisher's and listener's jar in the project.

* Open web browser and login with `u:u` credentials at `http://localhost:8085/`

* In *Tasklist* start an instance of a process `JMS Send`. This will enqueue a message that is a json - `{'bkey':'first','msg':'start'}` on a `test` queue that is run on an `failover://tcp://localhost:61616` address.

* In our project there is a process called `JMS Listen`. It is started by a *Message Event* that is waitng for `start` message by listenening to the local queue. As in previous step we enqueued such message, the Camunda Engine will start an instance of a `JMS Listen` process.

* At this point it is clear how the JMS connector works, it enqueued a message on a queue that is listened by the engine. Whenever engine receives a message it will start an instance of a new particular process. 

* Now that the instance of `JMS Listen` is active it awaits for `continue` message. Let's send one! Customize in Camunda Modeler the JSON of `JMS Send` process with `{'bkey':'first','msg':'start'}` and deploy it to the engine. Now if you run `JMS Send` again you will notice that the instance of `JMS Listen` is gone, as the listener received `continue` message from engine that dequeued the message from the queue.

## Roadmap

See the [open issues](https://github.com/github_username/repo_name/issues) for a list of proposed features (and known issues).


## Contributing

Any contributions you make are **greatly appreciated**.

## License

See `LICENSE` for more information.

## Contact

[@devapo](https://devapo.pl/)

Project Link: [https://github.com/](https://github.com/)

## Acknowledgements

* []()
* []()
* []()

[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo.svg?style=for-the-badge
[contributors-url]: https://github.com/dbader/readme-template
[forks-shield]: https://img.shields.io/github/forks/github_username/repo.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/github_username