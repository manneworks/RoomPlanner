package roomplanner.remote

class RoomPlannerRemoteService {

   static remote = [
        protocol: 'hessian',
        iface: roomplanner.api.IRoomPlannerService,
        host: 'localhost',
        port: '8080',
        webcontext: 'RoomPlanner',
    ]
}