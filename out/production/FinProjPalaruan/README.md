# Banking System Application


## Custom Exception Classes

The application implements specialized exception handling for banking operations:

1. **InsufficientFundsException**:
   - Thrown when an account lacks sufficient balance for a requested operation
   - Provides details about the requested amount and available balance

2. **InvalidAccountException**:
   - Thrown when operations are attempted on non-existent or invalid accounts
   - Helps identify issues with account lookup or validation

3. **AccountClosedException**:
   - Thrown when operations are attempted on accounts that have been closed
   - Prevents transactions on inactive accounts

4. **TransactionLimitException**:
   - Thrown when operations exceed defined transaction limits
   - Helps enforce daily withdrawal limits, transfer limits, etc.

5. **InvalidAmountException**:
   - Thrown when invalid monetary amounts are provided
   - Validates that amounts are positive and within acceptable ranges

