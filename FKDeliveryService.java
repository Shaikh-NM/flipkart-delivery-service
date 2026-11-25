class FLDeliveryService{
    private final Map<String, Order> orders = new HashMap<>();
    private static final class Order{
        String orderName;
        String pincode; 
        String creationTime;

        public Order(String orderName, String pincode, String creationTime){
            this.orderName = orderName;
            this.pincode = pincode;
            this.creationTime = creationTime;
        }
    }
    public synchronized String createOrder(String orderName, String pincode, String creationTime){
        if (orders.get(orderName) != null){
            return "ORDER_ALREADY_EXISTS"
        }

        Order newOrder = new Order(orderName, pincode, creationTime);
        orders.put(orderName, newOrder);
        return "ORDER_CREATED"
    }

    public synchronized String createOrderScheduled(String orderName, String pincode, String scheduledStartTime, int durationMinutes){
        if (orders.get(orderName) != null){
            return "ORDER_ALREADY_EXISTS"
        }
        if (durationMinutes <= 0){
            return "INVALID_SCHEDULE"
        }
    }


    private final Map<String, Agent> agents = new HashMap<>();
    private static final class Agent{
        String agentName;
        Set<String> pincodes = new ArrayList<>();

        public Agent(String agentName, String pincode){
            this.agentName = agentName;
            this.pincodes.add(pincode);
        }
    }
    public synchronized String createAgent(String agentName, String pincode){
        if (agents.get(agentName) != null){
            return "AGENT_ALREADY_EXISTS";
        }

        Agent newAgent = new Agent(agentName, pincode);
        agents.put(agentName, newAgent);
        return "AGENT_CREATED";
    }
    public synchronized String addAgentPincode(String agentName, String pincode){
        Agent agent = agents.get(agentName);
        if (agent == null){
            return "AGENT_NOT_FOUND";
        }

        Set<String> pincodes = agent.pincodes;
        if (pincodes.contains(pincode)){
            return "PINCODE_ALREADY_PRESENT";
        }

        agent.pincodes.add(pincode);
        return "PINCODE_ADDED";
    }

    


    public static void main(String[] args){
        FKDeliveryService service = new FKDeliveryService();
    }
}