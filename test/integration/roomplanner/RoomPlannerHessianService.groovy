package roomplanner

class RoomPlannerHessianService {
    static remote = [
        protocol: 'hessian',
        iface: roomplanner.api.iRoomPlannerService,
        host: 'localhost',
        port: '8080',
        webcontext: 'RoomPlanner',
    ]
}