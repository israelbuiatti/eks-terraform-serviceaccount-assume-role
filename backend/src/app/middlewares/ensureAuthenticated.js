import { verify } from "jsonwebtoken";
import authConfig from "../config/auth";
import AppError from "../exception/AppError";

export default function ensureAuthenticated(request, response, next) {

    // const authHeader = request.headers.authorization;

    // if (!authHeader) {
    //     throw new AppError('JWT token is missing', 401);
    // }

    // const [type, token] = authHeader.split(' ');

    // try {
    //     const decoded = verify(token, authConfig.jwt.secret);

    //     const { sub } = decoded;

    //     request.user = {
    //         id: sub
    //     }

        return next();
    // } catch (error) {
    //     throw new AppError('Invalid JWT token', 401);
    // }


}
