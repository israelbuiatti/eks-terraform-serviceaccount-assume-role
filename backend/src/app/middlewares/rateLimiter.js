import redis from 'redis';
import { RateLimiterRedis } from 'rate-limiter-flexible';
import AppError from '../exception/AppError';

if (process.env.NODE_ENV !== 'production') {
    import 'dotenv/config';
}

const redisClient = redis.createClient({
    host: process.env.REDIS_HOST,
    port: Number(process.env.REDIS_PORT),
    user: process.env.REDIS_USER,
    no_ready_check: true,
    password: process.env.REDIS_PASS || undefined,
});

const limiter = new RateLimiterRedis({
    storeClient: redisClient,
    keyPrefix: 'ratelimit',
    points: 5,
    duration: 10,
});

export default async function rateLimiter(request, response, next) {
    try {
        const teste = await limiter.consume(request.ip);
        return next();
    } catch (err) {
        throw new AppError('Too many requests', 429);
    }
}
